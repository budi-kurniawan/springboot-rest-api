package com.example.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Question {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String text;

	protected Question() {}

	public Question(String text) {
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