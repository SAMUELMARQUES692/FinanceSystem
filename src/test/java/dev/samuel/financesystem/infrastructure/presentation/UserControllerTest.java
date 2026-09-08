package dev.samuel.financesystem.infrastructure.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.samuel.financesystem.configuration.BaseIntegrationTest;
import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.ScopeRepository;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import dev.samuel.financesystem.infrastructure.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void createUser() throws Exception {
        UserRequest request = UserRequest.builder()
                .name("Name Test")
                .email("email@Test.com")
                .password("password Test")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void updateUser() throws Exception {
        User userInfra = userRepository.save(
                User.builder()
                        .name("Name Test")
                        .email("Email Test")
                        .password("Password Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        UserRequest request = UserRequest.builder()
                .name("Name Test")
                .email("email@Test.com")
                .password("password Test")
                .build();

        mockMvc.perform(put("/users/{id}", userInfra.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInfra.getId()))
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void deleteUser() throws Exception {
        User userInfra = userRepository.save(
                User.builder()
                        .name("Name Test")
                        .email("Email Test")
                        .password("Password Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(delete("/users/{id}", userInfra.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfra)))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByEmail() throws Exception {
        User userInfra = userRepository.save(
                User.builder()
                        .name("Name Test")
                        .email("Email Test")
                        .password("Password Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/users/{email}/email", userInfra.getEmail())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInfra.getId()))
                .andExpect(jsonPath("$.name").value(userInfra.getName()))
                .andExpect(jsonPath("$.email").value(userInfra.getEmail()));

    }
}