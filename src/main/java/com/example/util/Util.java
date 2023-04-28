package com.example.util;

import java.util.List;

import com.example.entity.Question;

public class Util {
	public static String createSessionId() {
		return "" + System.nanoTime();
	}
	
	public static Question getQuestionByID(List<Question> questions, Long questionId) {
		for (Question question : questions) {
			if (questionId.equals(question.getId())) {
				return question;
			}
		}
		return null;
	}
}
