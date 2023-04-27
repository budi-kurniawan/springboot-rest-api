package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Response(String output, String statusCode, String memory, 
		String cpuTime, String compilationStatus) { }
