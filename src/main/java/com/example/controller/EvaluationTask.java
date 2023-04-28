package com.example.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.model.Request;
import com.example.model.Response;

public class EvaluationTask implements Callable<Response>{
	private static final String COMPILE_OK = "0";
	private RestTemplate restTemplate;
	private List<Question> questions;
	private Answer answer;
	String clientId = "888902e60aea0fc7631f470674a19d3a";
	String clientSecret = "91c267cac73efbea688457cb674d6b4cc307f02072925c75c97ab378e8effa64";
	String url = "https://api.jdoodle.com/v1/execute";
	String args = "900 2000";
	String stdin = "";
	String language = "java";
	String versionIndex = "0";

	
	public EvaluationTask(List<Question> questions, Answer answer, RestTemplate restTemplate) {
		this.questions = questions;
		this.answer = answer;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public Response call() throws Exception {
		boolean compileOnly = true;
		String script = answer.getScript();
		Request request = new Request(clientId, clientSecret, script, 
				args, stdin, language, versionIndex, compileOnly);

		Response response = restTemplate.postForObject(url, request, Response.class);
		if (COMPILE_OK.equals(response.compilationStatus())) {
			compileOnly = false;
			request = new Request(clientId, clientSecret, script, 
					args, stdin, language, versionIndex, compileOnly);
			return restTemplate.postForObject(url, request, Response.class);
		} else {
			return new Response("Compile error: " + response.output(), "", "", "", "");
		}
	}
}
