package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.AnswerRepository;
import com.example.dao.QuestionRepository;
import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.util.Util;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	
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
		if (numAnswers >= 5) { // TODO move 5 to properties file
			return finalise(answer.getSessionId(), answers);
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
	
	private ModelAndView finalise(String sessionId, List<Answer> answers) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("final");
		mav.addObject("sessionId", sessionId);
		return mav;
		
	}

}
