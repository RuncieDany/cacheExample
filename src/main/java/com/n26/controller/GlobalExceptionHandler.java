package com.n26.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.exception.Past60Seconds;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);	
@ExceptionHandler(InvalidFormatException.class)	
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public void ParseExceptionhandler(Exception exception) {
	logger.error("Exception during execution ",exception.getMessage());
	
}

@ExceptionHandler(Past60Seconds.class)	
@ResponseStatus(HttpStatus.NO_CONTENT)
public void past60Secondshandler(Exception exception) {
	logger.error("TimeStamp is older than 60 secs ",exception);
	
}

@ExceptionHandler(MethodArgumentNotValidException.class)	
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public void invalidArgumentHandler(Exception exception) {
	logger.error("Exception during execution ",exception);
	
	
	
	
	
}

@ExceptionHandler(HttpMessageNotReadableException.class)	
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public void invalidJsonHandler(Exception exception) throws Exception {
	logger.error("Invalid Json during execution ",exception.getCause());
	
	
	
}

@ExceptionHandler(Exception.class)	
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public void generalExceptionHandler(Exception exception) {
	logger.error("Exception during execution ",exception);
	
}


}