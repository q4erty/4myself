package com.practice.project1.controller;

import com.practice.project1.model.entity.Email;
import com.practice.project1.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/auth", "/auth/email"})
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send/simple")
    public void sendSimpleEmail(@RequestBody Email email) {
        emailService.sendSimpleEmail(email);
    }

    @PostMapping("/send/html")
    public void sendHtmlEmail(@RequestBody Email email) throws MessagingException {
        emailService.sendHtmlEmail(email);
    }

    @PostMapping("/send/thymeleaf")
    public void sendThymeleafEmail(@RequestBody Email email) throws MessagingException {
        emailService.sendThymeleafEmail(email);
    }

    @PostMapping("/send/attachment")
    public void sendAttachmentEmail(@RequestBody Email email) throws MessagingException {
        emailService.sendAttachmentsEmail(email);
    }

}
