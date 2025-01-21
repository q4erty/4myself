package com.practice.project1.service;

import com.practice.project1.model.entity.Email;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

/**
 * Service interface for handling email-related operations.
 */
public interface EmailService {

    /**
     * Sends a simple email with plain text content.
     *
     * @param email the email object containing the recipient(s), subject, and body of the email
     */
    @Async
    void sendSimpleEmail(Email email);

    /**
     * Sends an HTML-formatted email.
     *
     * @param email the email object containing the recipient(s), subject, and HTML body of the email
     * @throws MessagingException if an error occurs during email creation or sending
     */
    @Async
    void sendHtmlEmail(Email email) throws MessagingException;

    /**
     * Sends an email using a Thymeleaf template for its content.
     *
     * <p>The Thymeleaf template is expected to include placeholders for subject, recipient username,
     * and message body, which will be dynamically replaced with values from the email object.</p>
     *
     * @param email the email object containing the recipient(s), subject, and body of the email
     * @throws MessagingException if an error occurs during email creation or sending
     */
    @Async
    void sendThymeleafEmail(Email email) throws MessagingException;

    /**
     * Sends an email with attachments.
     *
     * <p>The attachment is expected to be located at "/img/cat.jpg" in the classpath.
     * The email object contains recipient(s), subject, and body for the email.</p>
     *
     * @param email the email object containing the recipient(s), subject, and body of the email
     * @throws MessagingException if an error occurs during email creation or sending
     */
    @Async
    void sendAttachmentsEmail(Email email) throws MessagingException;
}
