package dev.samuel.financesystem.infrastructure.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.samuel.financesystem.configuration.BaseIntegrationTest;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import dev.samuel.financesystem.infrastructure.repository.TransactionRepository;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest extends BaseIntegrationTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void transfer() throws Exception {
        User userOrigin = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("user1@test.com")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        User userDestination = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("user2@test.com")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account origin = accountRepository.save(
                Account.builder()
                .userId(userOrigin.getId())
                .balance(BigDecimal.TEN)
                .agency("123123123")
                .number("22")
                .createdAt(LocalDateTime.now())
                .build()
        );

        Account destination = accountRepository.save(
                Account.builder()
                .userId(userDestination.getId())
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build()
        );

        TransactionRequest request = TransactionRequest.builder()
                .destinationId(destination.getId())
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .description("Description Test")
                .build();

        mockMvc.perform(post("/transactions/transfer")
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userOrigin.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destinationId").value(request.destinationId()))
                .andExpect(jsonPath("$.amount").value(request.amount()))
                .andExpect(jsonPath("$.type").value(request.type().name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void getReport() throws Exception {
        User userOrigin = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("user1@test.com")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        User userDestination = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("user2@test.com")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account origin = accountRepository.save(
                Account.builder()
                        .userId(userOrigin.getId())
                        .balance(BigDecimal.TEN)
                        .agency("123123123")
                        .number("22")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account destination = accountRepository.save(
                Account.builder()
                        .userId(userDestination.getId())
                        .balance(BigDecimal.TEN)
                        .agency("123123213")
                        .number("23")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Transaction transaction = transactionRepository.save(
                Transaction.builder()
                        .originId(origin.getId())
                        .destinationId(destination.getId())
                        .amount(BigDecimal.TEN)
                        .type(Type.TRANSFER)
                        .status(Status.COMPLETED)
                        .description("Description Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/transactions/report")
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userOrigin.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originId").value(transaction.getOriginId()))
                .andExpect(jsonPath("$[0].destinationId").value(transaction.getDestinationId()))
                .andExpect(jsonPath("$[0].amount").value(transaction.getAmount()))
                .andExpect(jsonPath("$[0].type").value(transaction.getType().name()))
                .andExpect(jsonPath("$[0].status").value(transaction.getStatus().name()))
                .andExpect(jsonPath("$[0].description").value(transaction.getDescription()));
    }
}