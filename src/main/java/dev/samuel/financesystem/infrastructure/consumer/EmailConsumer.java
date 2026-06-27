package dev.samuel.financesystem.infrastructure.consumer;

import dev.samuel.financesystem.core.enums.EmailStatus;
import dev.samuel.financesystem.infrastructure.gateway.EmailService;
import dev.samuel.financesystem.infrastructure.persistence.Email;
import dev.samuel.financesystem.infrastructure.message.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "transfer-queue")
    public void listenEmailQueue(@Payload EmailMessage emailMessage) {
        log.info("Received email message for userId: {}", emailMessage.getUserId());
        var email = new Email();
        BeanUtils.copyProperties(emailMessage, email);
        email.setStatusEmail(EmailStatus.PENDING);
        emailService.sendEmail(email);
    }
}
