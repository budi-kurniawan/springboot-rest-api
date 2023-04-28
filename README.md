#REST API with Springboot

This application uses Spring Boot, Spring MVC, JPA and Spring RestTemplate. To store data, an H2 database is used.
The database server operates as an in-memory database and, as such, all data will be lost when the application shuts down.

The application requires Java 17.

When the user starts a programming test, a session id is created and every answer is stored in the database, allowing the user to come back to retrieve previous results. The admin can create and delete a question. A question consists of the following components:

- A text: The wording of the question
- A template: A program skeleton to help the user
- A list of test cases stored in the database and hidden from the user

When the user submits the last answer, all the answers associated with the session id will be fetched from the database. For each question, the application evaluates the answer twice, first for compile error and second with the test cases associated with the question.

The admin user can create a new question by supplying a text, a template and zero or more test cases. Each test case consists of an argument list and the expected output. The argument list must be entered as a space limited list. For example, an add method may have the following list of arguments that make up 5 test cases:

- 1 2
- 2 3
- 3 4
- 4 5
- 5 6

The following matching output should accompany these arguments:

- 3
- 5
- 7
- 9
- 11

##Running the App
```
mvn spring-boot:run
```

By default, the app run on port 8080. Use this in a web browser to view the Welcome page:

```
http://localhost:8080
```


##Running the Test
```
mvn test
mvn -Dtest=IndexControllerTest test
```
