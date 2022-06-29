package com.practice.basexbackend.utils;

import com.practice.basexbackend.dto.auth.response.HttpResponse;
import com.practice.basexbackend.exception.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse handleRefreshTokenException(RefreshTokenException exception, WebRequest request) {
        return new HttpResponse(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                request.getDescription(false),
                exception.getMessage()
        );
    }
}
