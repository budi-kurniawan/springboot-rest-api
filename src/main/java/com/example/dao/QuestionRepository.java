package com.example.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {

	Question findById(long id);
}