package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.EmailFileDto;
import com.backend.proyectointegradorc1g6.exception.FailedSendMailMessageException;
import com.backend.proyectointegradorc1g6.service.IEmailService;
import com.backend.proyectointegradorc1g6.service.senderutilities.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService implements IEmailService {

    @Value("${mail.sender}")
    private String enterpriseEmail;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Map<String, String> sendEmail(String[] toUser, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(enterpriseEmail);
        mailMessage.setTo(toUser);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

        Map<String, String> response = new HashMap<>();
        response.put("estado", "enviado");

        return response;

    }

    @Override
    public Map<String, String> sendEmailCustomer(String[] toUser, String subject, String message, String name, String logo) {

        String messagaFormat = MessageTemplate.TEMPLATE_MESSAGE;

        messagaFormat = messagaFormat.replace("[Nombre del Usuario]", name)
                .replace("[Correo del Usuario]", toUser[0])
                .replace("[URL de inicio de sesion]", message)
                .replace("[Logo Royal Ride]", logo);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(enterpriseEmail);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(messagaFormat, true);

            mailSender.send(mimeMessage);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "enviado");

            return response;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> sendEmailWithFile(EmailFileDto emailFileDto) throws FailedSendMailMessageException {

        try {
            String fileName = emailFileDto.getFile().getOriginalFilename();
            Path path = Paths.get("src/main/resources/files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDto.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();

            settinMailSender(emailFileDto.getToUser(), emailFileDto.getSubject(), emailFileDto.getMessage(), file);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "enviado");
            response.put("archivo", fileName);

            return response;

        } catch (Exception e) {
            throw new FailedSendMailMessageException("Error al enviar el mail");
        }


    }

    private void settinMailSender(String[] toUser, String subject, String message, File file) throws FailedSendMailMessageException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(enterpriseEmail);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new FailedSendMailMessageException("Error al enviar file e email");
        }
    }
}
