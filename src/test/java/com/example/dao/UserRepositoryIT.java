package com.example.dao;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryIT {

	@Autowired
	private UserRepository userRepository;

    @Test
    public void findAllTest() throws Exception {
    	
    	userRepository.findAll().forEach(u -> {
    		System.out.println(u.getFirstName() + " " + u.getLastName());
    	});
//        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }
}
