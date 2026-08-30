package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.enums.EmailStatus;
import dev.samuel.financesystem.infrastructure.persistence.Email;
import dev.samuel.financesystem.infrastructure.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    EmailRepository emailRepository;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Captor
    ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "emailFrom", "remetente@teste.com");
    }

    @Test
    void sendEmail() {
        Email email = Email.builder()
                .emailId(1L)
                .userId(1L)
                .emailTo("destinatario@teste.com")
                .emailSubject("Titulo Teste")
                .body("Texto Teste")
                .statusEmail(EmailStatus.SENT)
                .build();

        Mockito.when(emailRepository.save(email)).thenReturn(email);

        emailService.sendEmail(email);

        Mockito.verify(javaMailSender).send(messageCaptor.capture());
        SimpleMailMessage mensagemEnviada = messageCaptor.getValue();
        assertEquals(email.getEmailTo(), mensagemEnviada.getTo()[0]);
        assertEquals(email.getEmailSubject(), mensagemEnviada.getSubject());
        assertEquals(email.getBody(), mensagemEnviada.getText());

        Mockito.verify(emailRepository).save(emailCaptor.capture());
        Email emailSalvo = emailCaptor.getValue();
        assertEquals(email.getEmailId(), emailSalvo.getEmailId());
        assertEquals(EmailStatus.SENT, emailSalvo.getStatusEmail());
        assertNotNull(emailSalvo.getEmailFrom());
    }
}