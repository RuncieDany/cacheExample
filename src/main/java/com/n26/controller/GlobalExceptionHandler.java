package com.n26.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.exception.Past60Seconds;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);	
@ExceptionHandler(MismatchedInputException.class)	
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public void ParseExceptionhandler(Exception exception) {
	logger.error("Exception during execution ",exception);
	
}

@ExceptionHandler(Past60Seconds.class)	
@ResponseStatus(HttpStatus.NO_CONTENT)
public void past60Secondshandler(Exception exception) {
	logger.error("Exception during execution ",exception);
	
}
}