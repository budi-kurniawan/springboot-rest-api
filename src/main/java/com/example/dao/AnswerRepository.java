package com.example.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

	List<Answer> findBySessionId(String sessionId);

}