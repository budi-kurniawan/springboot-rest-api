package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.AnswerRepository;
import com.example.dao.QuestionRepository;
import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.model.Response;
import com.example.util.Util;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private static final int NUM_TO_ANSWER = 2; // TODO move to properties file
	private static final int NUM_THREADS = 100;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	
	private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
	
	@Value("${jdoodle.clientId}")
	private String clientId;

	@Value("${jdoodle.clientSecret}")
	private String clientSecret;

	@Value("${jdoodle.url}")
	private String url;

	@RequestMapping("/user")
	public String index() {
		return "user";
	}
	
	@RequestMapping("/question")
	public ModelAndView showQuestion(@ModelAttribute Answer answer, Model model) {
		boolean starting = answer.getSessionId() == null;
		if (starting) {
			answer.setSessionId(Util.createSessionId());
		} else {
			answerRepository.save(answer);
		}
		List<Question> questions = (List<Question>) questionRepository.findAll();
		questions.stream().mapToLong(e -> e.getId());

		List<Answer> answers = answerRepository.findBySessionId(answer.getSessionId());
		int numAnswers = answers.size();
		if (numAnswers == NUM_TO_ANSWER) {
			return evaluate(answer.getSessionId(), questions, answers);
		}
		
		// show another question by choosing a question that has not been answered
		Set<Long> answeredQuestionIds = answers.stream().map(e -> e.getQuestionId()).collect(Collectors.toSet());
		log.info("answered question ids:" + answeredQuestionIds);
		List<Question> unusedQuestions = questions.stream().filter(e -> !answeredQuestionIds.contains(e.getId())).collect(Collectors.toList());
		int randomIndex = ThreadLocalRandom.current().nextInt(0, unusedQuestions.size()); // 0 to size-1
		Question question = unusedQuestions.get(randomIndex);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("question");
		mav.addObject("question", question);
		mav.addObject("questionNo", numAnswers + 1);
		mav.addObject("sessionId", answer.getSessionId());
		return mav;
	}
	
	private ModelAndView evaluate(String sessionId, List<Question> questions, List<Answer> answers) {
		log.info("start evaluating. client id:" + clientId);
		List<Response> responses = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		for (Answer answer : answers) {
			Callable<Response> task = new EvaluationTask(questions, answer, restTemplate, 
					clientId, clientSecret, url);
			Future<Response> future = executor.submit(task);
			try {
				Response response = future.get();
				responses.add(response);
			} catch(InterruptedException | ExecutionException e) {
				log.error(e.getMessage());
			}
		}
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("evaluate");
		mav.addObject("sessionId", sessionId);
		mav.addObject("responses", responses);
		return mav;
	}

}
