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
import dev.samuel.financesystem.infrastructure.request.UpdateAccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                        .email("Email Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .user(userInfra)
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .pix("Pix Test")
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
                .andExpect(jsonPath("$.balance").value(accountInfra.getBalance()))
                .andExpect(jsonPath("$.agency").value(accountInfra.getAgency()))
                .andExpect(jsonPath("$.number").value(accountInfra.getNumber()))
                .andExpect(jsonPath("$.pix").value(accountInfra.getPix()));
    }

    @Test
    void findByuserId() throws Exception {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Email Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .user(userInfra)
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .pix("Pix Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/accounts/user")
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInfra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(accountInfra.getBalance()))
                .andExpect(jsonPath("$.agency").value(accountInfra.getAgency()))
                .andExpect(jsonPath("$.number").value(accountInfra.getNumber()))
                .andExpect(jsonPath("$.pix").value(accountInfra.getPix()));
    }

    @Test
    void updateAccount() throws Exception {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Email Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .user(userInfra)
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .pix("Pix Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        UpdateAccountRequest updateRequest = UpdateAccountRequest.builder()
                .pix("Updated Pix")
                .build();

        AccountResponse response = AccountResponse.builder()
                .id(accountInfra.getId())
                .userName(userInfra.getName())
                .userCpf(userInfra.getCpf())
                .balance(accountInfra.getBalance())
                .agency(accountInfra.getAgency())
                .number(accountInfra.getNumber())
                .pix(updateRequest.pix())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(put("/accounts/{id}", accountInfra.getId())
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.userName").value(response.userName()))
                .andExpect(jsonPath("$.userCpf").value(response.userCpf()))
                .andExpect(jsonPath("$.balance").value(response.balance()))
                .andExpect(jsonPath("$.agency").value(response.agency()))
                .andExpect(jsonPath("$.number").value(response.number()))
                .andExpect(jsonPath("$.pix").value(accountInfra.getPix()));
    }

    @Test
    void findById() throws Exception {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Email Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .user(userInfra)
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .pix("Pix Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        AccountResponse response = AccountResponse.builder()
                .id(accountInfra.getId())
                .userName(userInfra.getName())
                .userCpf(userInfra.getCpf())
                .balance(accountInfra.getBalance())
                .agency(accountInfra.getAgency())
                .number(accountInfra.getNumber())
                .pix(accountInfra.getPix())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/accounts/{id}", accountInfra.getId())
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.userName").value(response.userName()))
                .andExpect(jsonPath("$.userCpf").value(response.userCpf()))
                .andExpect(jsonPath("$.balance").value(response.balance()))
                .andExpect(jsonPath("$.agency").value(response.agency()))
                .andExpect(jsonPath("$.number").value(response.number()))
                .andExpect(jsonPath("$.pix").value(accountInfra.getPix()));
    }

    @Test
    void findByPix() throws Exception {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = userRepository.save(
                User.builder()
                        .name("Name Teste")
                        .email("Email Teste")
                        .password("Senha Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Account accountInfra = accountRepository.save(
                Account.builder()
                        .user(userInfra)
                        .balance(BigDecimal.TEN)
                        .agency("3123123213")
                        .number("12312")
                        .pix("Pix Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        AccountResponse response = AccountResponse.builder()
                .id(accountInfra.getId())
                .userName(userInfra.getName())
                .userCpf(userInfra.getCpf())
                .balance(accountInfra.getBalance())
                .agency(accountInfra.getAgency())
                .number(accountInfra.getNumber())
                .pix(accountInfra.getPix())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/accounts/pix/{pix}", accountInfra.getPix())
                        .with(jwt()
                                .jwt(jwt -> jwt.subject(String.valueOf(userInfra.getId())))
                                .authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.userName").value(response.userName()))
                .andExpect(jsonPath("$.userCpf").value(response.userCpf()))
                .andExpect(jsonPath("$.balance").value(response.balance()))
                .andExpect(jsonPath("$.agency").value(response.agency()))
                .andExpect(jsonPath("$.number").value(response.number()))
                .andExpect(jsonPath("$.pix").value(accountInfra.getPix()));
    }
}