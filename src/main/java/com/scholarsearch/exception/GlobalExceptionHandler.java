package com.scholarsearch.exception;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    protected String handleIllegalArgumentException(Exception exception, ServletWebRequest request, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("path", request.getRequest().getRequestURI());
        return "error";
    }

    @ExceptionHandler({NumberFormatException.class, JsonProcessingException.class, JsonParseException.class, JsonGenerationException.class})
    protected String handleNumberFormatException(Exception exception, ServletWebRequest request, Model model) {
        model.addAttribute("status", 422);
        model.addAttribute("error", "Unprocessable Entity");
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("path", request.getRequest().getRequestURI());
        return "error";
    }

    @ExceptionHandler({RestClientResponseException.class})
    protected String handleHttpClientResponseException(RestClientResponseException exception, ServletWebRequest request, Model model) {
        model.addAttribute("status", exception.getStatusCode().value());
        model.addAttribute("error", exception.getStatusText());
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("path", request.getRequest().getRequestURI());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    protected String handleException(Exception exception, ServletWebRequest request, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("path", request.getRequest().getRequestURI());
        return "error";
    }
}