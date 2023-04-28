package com.example.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testGetRandomQuestionId() {
		Set<Integer> questionIds = new HashSet( Set.of(1, 2, 4, 100));
		Set<Integer> answeredQuestionIds = new HashSet(Set.of(1, 2, 5));
		
		questionIds.removeAll(answeredQuestionIds);
		System.out.println("hi" + questionIds);
		
	}
	
	
	@Test
	public void generateRandomIndex() {
		
		for (int i = 0; i < 20; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0, 10); // 0 to 9 inclusive
			System.out.println(rand);
		}
	}

	
}