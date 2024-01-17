package com.javaeat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleServerErrorException(Exception e) {
        log.error(e.getMessage());
        return new ErrorResponse(500, e.getMessage());
    }

    // You can define other exception handlers here

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return new ErrorResponse(404, e.getMessage());
    }

    @ExceptionHandler(value = HandlerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handlerException(HandlerException e) {

        log.error(e.getMessage());
        return new ErrorResponse(400, e.getMessage());
    }
    @AllArgsConstructor
    @Setter
    @Getter
    static class ErrorResponse {
        private Integer code;
        private String message;
    }
}
