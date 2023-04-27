package com.example.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastName(String lastName);

	User findById(long id);
}