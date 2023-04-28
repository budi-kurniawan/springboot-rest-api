package com.example.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.QuestionRepository;
import com.example.entity.Question;

@RestController
public class AdminController {
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/admin")
	public ModelAndView index() {
		return getModelAndViewForAdmin();
	}

	@GetMapping("/deleteQuestion/{id}")
	public ModelAndView deleteQuestion(@PathVariable String id) {
		log.info("delete question id:" + id);
		
		questionRepository.deleteById(Long.parseLong(id));
		return getModelAndViewForAdmin();
	}
	
	@GetMapping("/addQuestion")
	public ModelAndView addQuestion() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("addQuestionForm");
		return mav;
	}

	@PostMapping("/addQuestion")
	public ModelAndView saveQuestion(@ModelAttribute Question question, Model model) {
		question.setTestCases(new ArrayList<>());
		log.info("user added this question " + question);
		questionRepository.save(question);
		return getModelAndViewForAdmin();
	}

	@GetMapping("/testCases/{questionId}")
	public ModelAndView addTestCases(@PathVariable String questionId) {
		ModelAndView mav = new ModelAndView();
		Question question = questionRepository.findById(Long.parseLong(questionId));
		mav.setViewName("testCases");
		mav.addObject("question", question);
		return mav;
	}

	@PostMapping("/testCases")
	public ModelAndView saveTestCases(@ModelAttribute Question question, Model model) {
		question.setTestCases(new ArrayList<>());
		log.info("user added this question " + question);
		questionRepository.save(question);
		return getModelAndViewForAdmin();
	}
	
	private ModelAndView getModelAndViewForAdmin() {
		List<Question> questions = (List<Question>) questionRepository.findAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin");
		mav.addObject("questions", questions);
		return mav;
	}

}
