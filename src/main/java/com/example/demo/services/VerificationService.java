package com.example.demo.services;

import com.example.demo.Dto.VerificationDetailsDto;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface VerificationService {
    String generateToken();

    String sendToken(VerificationDetailsDto verificationDetailsDto) throws IOException;

    Boolean verify(VerificationDetailsDto verificationDetailsDto);
}
