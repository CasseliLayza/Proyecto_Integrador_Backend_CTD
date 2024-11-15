package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.EmailDto;
import com.backend.proyectointegradorc1g6.dto.input.EmailFileDto;
import com.backend.proyectointegradorc1g6.exception.FailedSendMailMessageException;
import com.backend.proyectointegradorc1g6.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mail")
@CrossOrigin(originPatterns = "*")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/send/message")
    public ResponseEntity<?> recieveRequestEmail(@RequestBody EmailDto emailDto) {
        return new ResponseEntity<>(emailService.sendEmail(emailDto.getToUser(), emailDto.getSubject(), emailDto.getMessage()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/send/message/customer")
    public ResponseEntity<Map<String, String>> recieveRequestCustomerEmail(@RequestBody EmailDto emailDto) throws FailedSendMailMessageException {
        return new ResponseEntity<>(emailService.sendEmailCustomer(emailDto.getToUser(), emailDto.getSubject(), emailDto.getMessage(),emailDto.getName(),emailDto.getLogo()), HttpStatus.ACCEPTED);

    }

    @PostMapping("/send/file")
    public ResponseEntity<Map<String, String>> reciveRequestWithFile(@ModelAttribute EmailFileDto emailFileDto) throws FailedSendMailMessageException {

        return new ResponseEntity<>(emailService.sendEmailWithFile(emailFileDto),HttpStatus.ACCEPTED);



    }

}
