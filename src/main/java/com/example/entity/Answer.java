package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Answer {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String sessionId;
	private Long questionId;
	private String script;
	
	protected Answer() {}

	public Answer(String sessionId, Long questionId, String script) {
		this.sessionId = sessionId;
		this.questionId = questionId;
		this.script = script;
	}

	@Override
	public String toString() {
		return String.format(
				"Answer[id=%d, sessionId='%s', questionId='%d', script='%s']",
				id, sessionId, questionId, script);
	}

	public Long getId() {
		return id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Long getQuestionId() {
		return questionId;
	}
	
	public String getScript() {
		return script;
	}	
}