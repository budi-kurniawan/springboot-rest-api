package com.example.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Question {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String text;
	
	@Column(columnDefinition = "TEXT")
	private String template;
	
	@OneToMany(mappedBy="question", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<TestCase> testCases;

	protected Question() {}

	public Question(String text, String template, List<TestCase> testCases) {
		this.text = text;
		this.template = template;
		this.testCases = testCases;
		if (testCases != null) {
			testCases.forEach(e -> e.setQuestion(this));
		}
	}

	@Override
	public String toString() {
		return String.format(
				"Question[id=%d, text='%s', testCases='\n - %s']",
				id, text, testCases.stream().map(e -> e.toString()).collect(Collectors.joining("\n - ")));
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}
	
	public String getTemplate() {
		return template;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}
	
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
		if (testCases != null) {
			testCases.forEach(e -> e.setQuestion(this));
		}
	}
}