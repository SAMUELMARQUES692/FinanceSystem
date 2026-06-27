package dev.samuel.financesystem.infrastructure.persistence;

import dev.samuel.financesystem.core.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emails_seq")
    @SequenceGenerator(name = "emails_seq", sequenceName = "emails_seq", allocationSize = 1)
    @Column(name = "id")
    private Long emailId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String emailFrom;

    @Column(nullable = false)
    private String emailTo;

    @Column(nullable = false)
    private String emailSubject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Column(name = "sent_at", updatable = false)
    private LocalDateTime sendAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus statusEmail;
}
