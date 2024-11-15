package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.EmailFileDto;
import com.backend.proyectointegradorc1g6.exception.FailedSendMailMessageException;

import java.util.Map;

public interface IEmailService {

    Map<String,String> sendEmail(String[] toUser, String subject, String message);
    Map<String,String> sendEmailCustomer(String[] toUser, String subject, String message, String name, String logo) throws FailedSendMailMessageException;

    Map<String, String> sendEmailWithFile(EmailFileDto emailFileDto) throws FailedSendMailMessageException;
}
