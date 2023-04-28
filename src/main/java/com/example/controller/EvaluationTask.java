package com.example.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.entity.TestCase;
import com.example.model.Request;
import com.example.model.Response;
import com.example.util.Util;

public class EvaluationTask implements Callable<Response>{
	private static final Logger log = LoggerFactory.getLogger(EvaluationTask.class);
	private static final String COMPILE_OK = "0";
	private RestTemplate restTemplate;
	private List<Question> questions;
	private Answer answer;
	private String clientId;
	private String clientSecret;
	private String url;
	private String stdin = "";
	private String language = "java";
	private String versionIndex = "0";

	
	public EvaluationTask(List<Question> questions, Answer answer, RestTemplate restTemplate,
			String clientId, String clientSecret, String url) {
		this.questions = questions;
		this.answer = answer;
		this.restTemplate = restTemplate;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.url = url;
	}
	
	@Override
	public Response call() throws Exception {
		boolean compileOnly = true;
		String script = answer.getScript();
		Request request = new Request(clientId, clientSecret, script, 
				"", stdin, language, versionIndex, compileOnly);

		Response response = restTemplate.postForObject(url, request, Response.class);
		Question question = Util.getQuestionByID(questions, answer.getQuestionId());
		if (COMPILE_OK.equals(response.getCompilationStatus())) {
			compileOnly = false;
			
			int correctAnswers = 0;
			if (question == null) {
				return new Response("System error. Question not found", "", "", "", "");
			} else {
				List<TestCase> testCases = question.getTestCases();
				for (TestCase testCase : testCases) {
					String args = testCase.getInput();
					String expected = testCase.getOutput();
					request = new Request(clientId, clientSecret, script, 
							args, stdin, language, versionIndex, compileOnly);
					response = restTemplate.postForObject(url, request, Response.class);
					if (expected.equals(response.getOutput().trim())) {
						correctAnswers++;
					}
				}
				String message = "Correct answers " + correctAnswers + " of " + testCases.size();
				response.setOutput(message);
				response.setQuestionText(question.getText());
				return response;
			}
		} else {
			response.setOutput("Compile Error: " + response.getOutput());
			response.setQuestionText(question.getText());
			return response;
		}
	}
}
