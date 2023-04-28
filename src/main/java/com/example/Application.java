package com.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.dao.QuestionRepository;
import com.example.entity.Question;
import com.example.entity.TestCase;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
	
//	@Bean
//	public CommandLineRunner demo(UserRepository repository) {
//		return (args) -> {
//			// save a few users
//			repository.save(new User("Jack", "Bauer"));
//			repository.save(new User("Chloe", "O'Brian"));
//			repository.save(new User("Kim", "Bauer"));
//			repository.save(new User("David", "Palmer"));
//			repository.save(new User("Michelle", "Dessler"));
//
//			// fetch all customers
//			log.info("Users found with findAll():");
//			log.info("-------------------------------");
//			for (User customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			User user = repository.findById(1L);
//			log.info("User found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(user.toString());
//			log.info("");
//
//			// fetch users by last name
//			log.info("User found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
//			// for (User bauer : repository.findByLastName("Bauer")) {
//			// 	log.info(bauer.toString());
//			// }
//			log.info("");
//		};
//	}

	@Bean
	public CommandLineRunner addQuestions(QuestionRepository repository) {
		return (args) -> {
			// save a few questions
			String template1 = """
					public class MyClass {
						public static int m(int a, int b) {
							// your implementation here
						}
						public static void main(String[] args) {
							int a = Integer.parseInt(args[0]);
							int b = Integer.parseInt(args[1]);
							System.out.println(m(a, b));
						}
					}
					""";
			for (int i = 1; i < 11; i++) {
				repository.save(new Question("Write method " + i, template1, List.of(new TestCase("1 2", "3"),
						new TestCase("100 20", "120"),
						new TestCase("10 30", "40"),
						new TestCase("-1 10", "9"),
						new TestCase("-100 -100", "-200"))));				
			}

			// fetch all questions
			log.info("Questions found with findAll():");
			log.info("-------------------------------");
			for (Question question : repository.findAll()) {
				log.info(question.toString());
			}
		};
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
