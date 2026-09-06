package dev.samuel.financesystem.infrastructure.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.samuel.financesystem.configuration.BaseIntegrationTest;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountControllerTest extends BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void createAccount() throws Exception {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Emael Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        AccountRequest request = AccountRequest.builder()
                .balance(BigDecimal.TEN)
                .agency("3123123213")
                .number("12312")
                .build();

        mockMvc.perform(post("/accounts")
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance").value(request.balance()))
                .andExpect(jsonPath("$.agency").value(request.agency()))
                .andExpect(jsonPath("$.number").value(request.number()));
    }

    @Test
    void getBalance() throws Exception{
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Emael Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .userId(userInfra.getId())
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/accounts/me")
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInfra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(accountInfra.getUserId()))
                .andExpect(jsonPath("$.balance").value(accountInfra.getBalance()))
                .andExpect(jsonPath("$.agency").value(accountInfra.getAgency()))
                .andExpect(jsonPath("$.number").value(accountInfra.getNumber()));
    }
}