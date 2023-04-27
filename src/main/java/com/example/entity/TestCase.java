package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TestCase {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String text;

	protected TestCase() {}

	public TestCase(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return String.format(
				"Question[id=%d, test='%s']",
				id, text);
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}
}