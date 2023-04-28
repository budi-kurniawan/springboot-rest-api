package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response{ 
	

	private String output;
	private String statusCode;
	private String memory;
	private String cpuTime;
	private String compilationStatus;
	private String questionText;

	public Response() {
		
	}
	public Response(String output, String statusCode, String memory, String cpuTime, String compilationStatus) {
		this.output = output;
		this.statusCode = statusCode;
		this.memory = memory;
		this.cpuTime = cpuTime;
		this.compilationStatus = compilationStatus;
	}
	

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(String cpuTime) {
		this.cpuTime = cpuTime;
	}

	public String getCompilationStatus() {
		return compilationStatus;
	}

	public void setCompilationStatus(String compilationStatus) {
		this.compilationStatus = compilationStatus;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public String getOutput() {
		return output;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	
	
}
