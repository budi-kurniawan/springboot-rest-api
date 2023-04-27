package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TestCase {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String input;
	private String output;
	
	@ManyToOne
	@JoinColumn(name="question_id", nullable=false)
	private Question question;

	protected TestCase() {}

	public TestCase(String input, String output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public String toString() {
		return String.format(
				"TestCase[id=%d, input='%s', output='%s']",
				id, input, output);
	}

	public Long getId() {
		return id;
	}

	public String getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
}