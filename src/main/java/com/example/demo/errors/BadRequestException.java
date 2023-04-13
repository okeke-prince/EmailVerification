package com.example.demo.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class BadRequestException extends Exception{
    private HttpStatus errorCode;

    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String messagge , HttpStatus errorCode){
        super(messagge);
        this.errorCode = errorCode;
    }
}
