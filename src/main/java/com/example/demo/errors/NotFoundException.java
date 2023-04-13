package com.example.demo.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Setter
@Getter
public class NotFoundException extends Exception{
    private HttpStatus errorCode;

    public NotFoundException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundException(String message) {
        super(message);
    }
}
