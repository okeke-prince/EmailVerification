package com.example.demo.services.serviceImplementation;

import com.example.demo.Dto.VerificationDetailsDto;
import com.example.demo.entities.Email;
import com.example.demo.repositories.MailRepository;
import com.example.demo.services.VerificationService;
//import java.lang.String
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sibModel.*;

//import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@AllArgsConstructor
@Service
@Getter
@Setter
@Slf4j
public class VerificationServiceImplementation implements VerificationService {



    private final MailRepository mailRepository;

    @Override
    public String generateToken() {
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        final char[] mix = (letters + letters.toUpperCase(Locale.ROOT) + "0123456789").toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(mix[new Random().nextInt(letters.length())]);
        }
        return builder.toString();
    }

    @Override
    public String sendToken(VerificationDetailsDto verificationDetailsDto){
        String verificationCode = generateToken();

        try{
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setName("Verifo");
            sender.setEmail("princeinme128@gmail.com");

            List<SendSmtpEmailTo> toList = new ArrayList<>();

            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(verificationDetailsDto.getEmail());
            toList.add(to);


            Properties headers = new Properties();
            headers.setProperty("subject", "Account Verification!");

            Map<String,String> param = new HashMap<>();
            param.put("vcode",verificationCode);


            System.out.println("building mail");

            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.params(param);
            sendSmtpEmail.subject("This is my default subject");
            sendSmtpEmail.setTemplateId(1L);


            log.info(sendSmtpEmail.toString());
            log.info("sending email");
            postToMicroServices(sendSmtpEmail);
            log.info("email sent");

            Email email = new Email();
            email.setEmail(verificationDetailsDto.getEmail());
            email.setVerificationNumber(verificationCode);

            log.info("...saving to repo");
            mailRepository.save(email);
            return "success";

        }catch (Exception e){
            System.out.println("error occurred");
            e.printStackTrace();
            return "error";
        }

    }

    @Override
    public Boolean verify(VerificationDetailsDto verificationDetailsDto) {
        Email email = new Email();
        email.setEmail(verificationDetailsDto.getEmail());
        email.setVerificationNumber(verificationDetailsDto.getVerificationCode());

        String vNumber  = mailRepository.findByEmail(verificationDetailsDto.getEmail()).getVerificationNumber(); // why is it null
        if (Objects.equals(vNumber, verificationDetailsDto.getVerificationCode())){
            mailRepository.delete(email);
            log.info("verified successfully");
            return true;
        }
        mailRepository.delete(email);
        return false;
    }

    private void postToMicroServices(SendSmtpEmail jsonPayload){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("api-key", "API-KEY");

        HttpEntity<SendSmtpEmail> entity = new HttpEntity<>(jsonPayload,headers);
        Object responses = restTemplate.exchange("https://api.sendinblue.com/v3/smtp/email", HttpMethod.POST,entity,Object.class);
        log.info(responses.toString());
    }

}
