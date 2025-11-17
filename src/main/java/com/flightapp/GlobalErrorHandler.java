package com.flightapp;

import java.util.HashMap;
import java.util.Map;


import java.util.*;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
	
	@ExceptionHandler(exception=Exception.class)
	public Map<String,String> handlerException(MethodArgumentNotValidException exception) {
		Map<String,String> errorMap= new HashMap<>();
		List<ObjectError>errorList = exception.getBindingResult().getAllErrors();
		errorList.forEach(error->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			errorMap.put(fieldName, message);
		});
		return errorMap;
		
	}
}
