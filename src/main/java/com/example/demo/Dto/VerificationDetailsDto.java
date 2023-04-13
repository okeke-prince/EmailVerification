package com.example.demo.Dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
public class VerificationDetailsDto {
    private String email;
    private String verificationCode;
}
