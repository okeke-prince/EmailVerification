package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
//import org.springframework.context.annotation.Primary;

@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String verificationNumber;
}
