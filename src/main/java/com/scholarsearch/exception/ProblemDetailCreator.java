package com.scholarsearch.exception;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@UtilityClass
public class ProblemDetailCreator {

    public ProblemDetail createProblemDetail(HttpStatus status, String detail, String title, String instance) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(instance));
        problemDetail.setProperty("error", true);
        return problemDetail;
    }

    public <T> ResponseEntity<T> getResponseEntity(HttpStatus status, String detail, ProblemTitle title, String instance) {
        return ResponseEntity.of(createProblemDetail(status, detail, title.getName(), instance)).build();
    }

    public <T> ResponseEntity<T> getResponseEntity(HttpStatus status, String detail, String title, String instance) {
        return ResponseEntity.of(createProblemDetail(status, detail, title, instance)).build();
    }
}
