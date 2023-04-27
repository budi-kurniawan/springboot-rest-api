package com.example.entity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.CascadeType;
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
	private String text;
	
	@OneToMany(mappedBy="question", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<TestCase> testCases;

	protected Question() {}

	public Question(String text, Set<TestCase> testCases) {
		this.text = text;
		this.testCases = testCases;
		testCases.forEach(e -> e.setQuestion(this));
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
	
	public Set<TestCase> getTestCases() {
		return testCases;
	}
}