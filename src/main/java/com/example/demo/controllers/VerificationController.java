package com.example.demo.controllers;

import com.example.demo.Dto.VerificationDetailsDto;
import com.example.demo.services.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/send")
public class VerificationController {
    private final VerificationService verificationService;

//    public String generateToken(){
//        return verificationService.generateToken();
//    }

    @PostMapping
    public String sendToken(@RequestBody VerificationDetailsDto verificationDetailsDto) throws IOException {
        return verificationService.sendToken(verificationDetailsDto);
    }

    @PostMapping("/verify")
    public Boolean verify(@RequestBody VerificationDetailsDto verificationDetailsDto){
        return verificationService.verify(verificationDetailsDto);
    }
}
