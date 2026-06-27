package dev.samuel.financesystem.infrastructure.producer;

import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import dev.samuel.financesystem.infrastructure.message.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String routingKey = "transfer-queue";

    public void publishEvent(Transaction transaction, String emailTo) {
        var emailResponse = new EmailMessage();
        emailResponse.setUserId(transaction.getOriginId());
        emailResponse.setEmailTo(emailTo);
        emailResponse.setEmailSubject("Transaction made on your account");
        emailResponse.setBody("A transfer of R$ " + transaction.getAmount() +
                " was made. Description: " + transaction.getDescription());

        log.info("Publishing transfer event for account: {}", transaction.getOriginId());

        rabbitTemplate.convertAndSend(
                "",
                routingKey,
                emailResponse
        );
    }
}