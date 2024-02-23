package com.automated.restaurant.automatedRestaurant.presentation.exceptions.handler;

import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.ConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConflictException.class})
    protected ResponseEntity<Object> handleConflictException(
            ConflictException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .timestamp(e.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .instance(httpServletRequest.getRequestURI())
                .details(e.getDetails())
                .build();

        return handleExceptionInternal(
                e,
                apiExceptionResponse,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request
        );
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestException(
            BadRequestException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .timestamp(e.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .instance(httpServletRequest.getRequestURI())
                .details(e.getDetails())
                .build();

        return handleExceptionInternal(
                e,
                apiExceptionResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorizedException(
            UnauthorizedException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .timestamp(e.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .instance(httpServletRequest.getRequestURI())
                .details(e.getDetails())
                .build();

        return handleExceptionInternal(
                e,
                apiExceptionResponse,
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(
            NotFoundException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .timestamp(e.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .instance(httpServletRequest.getRequestURI())
                .details(e.getDetails())
                .build();

        return handleExceptionInternal(
                e,
                apiExceptionResponse,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }
}
