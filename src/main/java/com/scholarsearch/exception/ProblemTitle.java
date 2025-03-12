package com.scholarsearch.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemTitle {
    BAD_REQUEST("Bad Request"),
    NOT_FOUND("Not Found"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    UNPROCESSABLE_ENTITY("Unprocessable Entity");

    private final String name;
}
