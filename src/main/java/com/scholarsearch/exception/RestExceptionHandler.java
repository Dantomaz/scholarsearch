package com.scholarsearch.exception;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        ProblemDetail problemDetail = exception.getBody();
        Map<String, List<String>> invalidParams = exception.getBindingResult()
            .getAllErrors()
            .stream()
            .collect(Collectors.groupingBy(
                error -> ((FieldError) error).getField(),
                Collectors.mapping(error -> Objects.requireNonNull(error.getDefaultMessage()), Collectors.toList())
            ));
        problemDetail.setProperty("invalid-params", invalidParams);
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Void> handleIllegalArgumentException(Exception exception, ServletWebRequest request) {
        return ProblemDetailCreator.getResponseEntity(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            ProblemTitle.BAD_REQUEST,
            request.getRequest().getRequestURI()
        );
    }

    @ExceptionHandler({NumberFormatException.class, JsonProcessingException.class, JsonParseException.class, JsonGenerationException.class})
    protected ResponseEntity<Void> handleNumberFormatException(Exception exception, ServletWebRequest request) {
        return ProblemDetailCreator.getResponseEntity(
            HttpStatus.UNPROCESSABLE_ENTITY,
            exception.getMessage(),
            ProblemTitle.UNPROCESSABLE_ENTITY,
            request.getRequest().getRequestURI()
        );
    }

    @ExceptionHandler({RestClientResponseException.class})
    protected ResponseEntity<Void> handleHttpClientResponseException(RestClientResponseException exception, ServletWebRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf(exception.getStatusCode().value());

        return ProblemDetailCreator.getResponseEntity(
            httpStatus,
            exception.getMessage(),
            httpStatus.name(),
            request.getRequest().getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Void> handleException(Exception exception, ServletWebRequest request) {
        return ProblemDetailCreator.getResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage(),
            ProblemTitle.INTERNAL_SERVER_ERROR,
            request.getRequest().getRequestURI()
        );
    }
}
