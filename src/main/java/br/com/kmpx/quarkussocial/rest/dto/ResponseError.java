package br.com.kmpx.quarkussocial.rest.dto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;

public class ResponseError {

	private String message;
	private Collection<FieldError> errors;
	
	public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violation) {
		List<FieldError> errors = violation
				.stream()
				.map( cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
				.collect(Collectors.toList());
	
		String message = "Validation Error";
		
		var responseError = new ResponseError(message, errors);
		return responseError;
	}
	
	public ResponseError(String message, Collection<FieldError> errors) {
		this.message = message;
		this.errors = errors;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Collection<FieldError> getErrors() {
		return errors;
	}
	public void setErrors(Collection<FieldError> errors) {
		this.errors = errors;
	}
}
