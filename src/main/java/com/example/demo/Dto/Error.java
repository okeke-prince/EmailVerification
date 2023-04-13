package com.example.demo.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class Error {
    private Integer errorCode;
    private  String errorMessage;
    private Date errorTime;
}
