package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.enums.EmailStatus;
import dev.samuel.financesystem.infrastructure.persistence.Email;
import dev.samuel.financesystem.infrastructure.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(Email email) {
        email.setEmailFrom(emailFrom);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(email.getEmailTo());
            message.setSubject(email.getEmailSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);
            email.setStatusEmail(EmailStatus.SENT);
            email.setSendAt(LocalDateTime.now());
        } catch (Exception exception) {
            email.setStatusEmail(EmailStatus.FAILED);
            log.error("Failed to send email to: {}", email.getEmailTo(), exception);
        }

        emailRepository.save(email);
    }
}
