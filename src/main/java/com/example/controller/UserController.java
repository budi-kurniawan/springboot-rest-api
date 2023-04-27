package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.QuestionRepository;
import com.example.entity.Question;
import com.example.entity.User;

@Controller
public class UserController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@RequestMapping("/user")
	public String index() {
		return "user";
	}
	
	@RequestMapping("/start")
	public ModelAndView start() {
		
		Question question = questionRepository.findById(1L);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("question");
		mav.addObject("question", question);
		mav.addObject("sessionId", System.currentTimeMillis() + "");
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
