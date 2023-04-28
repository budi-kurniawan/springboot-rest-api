package com.example.controller;

import java.util.List;

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
		log.info("submitted answer:" + answer);
		boolean starting = answer.getSessionId() == null;
		log.info("starting:" + starting);
		if (starting) {
			log.info("=------ create new session id");
			answer.setSessionId(Util.createSessionId());
		} else {
			answerRepository.save(answer);
		}
		List<Question> questions = (List<Question>) questionRepository.findAll();
		
		List<Answer> answers = answerRepository.findBySessionId(answer.getSessionId());
		
		int numAnswers = answers.size();
		log.info("num of answers:" + numAnswers);
		if (numAnswers >= 5) {
			return finalise(answer);
		}
		
		
		int questionNo = numAnswers + 1;
		
		Question question = questions.get(4);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("question");
		mav.addObject("question", question);
		mav.addObject("questionNo", questionNo);
		mav.addObject("sessionId", answer.getSessionId());
		return mav;
	}
	
	private ModelAndView finalise(Answer answer) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("final");
		mav.addObject("sessionId", answer.getSessionId());
		return mav;
		
	}

	
	@GetMapping("/submitAnswer")
	public String submitAnswer() {
		// TODO
		// getAllQuestions
		// getAllAnswers having the same sessionId
		
		// if len(questions) < len(answers) 
		// abort
		
		// if len(answers) < 4, save answer, select a random question and display the question
		
		
		// if len(answers) == 4, save answer, then look up JDoodle REST API, show results
		
		StringBuilder sb = new StringBuilder(1000);
		
		return "Users:" + sb.toString();
	}

}
