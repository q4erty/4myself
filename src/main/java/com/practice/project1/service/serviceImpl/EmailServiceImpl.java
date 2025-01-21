package com.practice.project1.service.serviceImpl;

import com.practice.project1.model.entity.Email;
import com.practice.project1.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        mailSender.send(message);
    }

    @Override
    @Async
    public void sendHtmlEmail(Email email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        mimeMessage.setFrom(new InternetAddress("akkemer05@gmail.com"));
        for(String recipient : email.getTo()) {
            mimeMessage.addRecipients(MimeMessage.RecipientType.TO, recipient);
        }
        mimeMessage.setSubject(email.getSubject());
        mimeMessage.setContent(email.getBody(), "text/html; charset=utf-8");

        mailSender.send(mimeMessage);
    }

    @Override
    @Async
    public void sendThymeleafEmail(Email email) throws MessagingException {
        for(String recipient : email.getTo()) {
            Context context = new Context();
            context.setVariable("subject", email.getSubject());
            context.setVariable("username", recipient);
            context.setVariable("message", email.getBody());

            String process = templateEngine.process("ThymeleafMail", context);

            System.out.printf("ThymeleafMail: %s\n", process);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setFrom(new InternetAddress("akkemer05@gmail.com"));
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(process, true);

            mailSender.send(mimeMessage);
        }
    }

    @Override
    @Async
    public void sendAttachmentsEmail(Email email) throws MessagingException {
        for(String recipient : email.getTo()) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(new InternetAddress("akkemer05@gmai.com"));
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getBody());

            ClassPathResource classPathResource = new ClassPathResource("/static/img/cat.jpg");
            mimeMessageHelper.addAttachment(Objects.requireNonNull(classPathResource.getFilename()), classPathResource);
            mailSender.send(mimeMessage);
        }
    }

}
