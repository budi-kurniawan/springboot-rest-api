package com.example.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.example.model.Request;
import com.example.model.Response;

@SpringBootTest
public class RestTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testSimpleJavaClass() {
		assertThat(restTemplate).isNotNull();
		
		String clientId = "888902e60aea0fc7631f470674a19d3a";
		String clientSecret = "91c267cac73efbea688457cb674d6b4cc307f02072925c75c97ab378e8effa64";
		String url = "https://api.jdoodle.com/v1/execute";
		String script = """
				public class Test {
				
					public static int add(int a, int b) {
						return a + b;
					}
					public static void main(String[] args) {
						int a = Integer.parseInt(args[0]);
						int b = Integer.parseInt(args[1]);
					    System.out.println(add(a, b));
					}
				}
				
				""";
		String args = "900 2000";
		String stdin = "";
		String language = "java";
		String versionIndex = "0";
		boolean compileOnly = false;
		Request request = new Request(clientId, clientSecret, script, 
				args, stdin, language, versionIndex, compileOnly);
		
		Response response = restTemplate.postForObject(url, request, Response.class);
		
		System.out.println(response);
		System.out.println(response.output());
		
		
	}

}