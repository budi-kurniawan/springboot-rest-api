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
import com.example.entity.TestCase;
import com.example.model.SimpleQuestion;

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
	public ModelAndView saveQuestion(@ModelAttribute SimpleQuestion simpleQuestion, Model model) {
		log.info("uploaded:" + simpleQuestion);
		String arguments = simpleQuestion.arguments();
		String output = simpleQuestion.output();
		
		String[] args = arguments.split("\n");
		log.info("args length" + args.length);
		String[] outputs = output.split("\n");
		int minLength = Math.min(args.length, outputs.length);
		List<TestCase> testCases = new ArrayList<>();
		for (int i = 0; i < minLength; i++) {
			testCases.add(new TestCase(args[i], outputs[i]));
		}
		Question question = new Question(simpleQuestion.text(), simpleQuestion.template(), testCases);
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
