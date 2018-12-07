package com.zhujiu.waka.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String serverExceptionHandler(Exception ex) {
		log.error(ex.getMessage(), ex);
		return ex.getMessage();
	}
}
