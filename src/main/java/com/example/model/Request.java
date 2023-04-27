package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Request(String clientId, String clientSecret, String script, 
		String args, String stdin, String language, String versionIndex, boolean compileOnly) { }
