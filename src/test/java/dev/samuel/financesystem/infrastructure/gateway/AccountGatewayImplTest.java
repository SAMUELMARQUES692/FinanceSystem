package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountGatewayImplTest {

    @InjectMocks
    AccountGatewayImpl accountGateway;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountMapper accountMapper;

    @Captor
    ArgumentCaptor<Account> argumentCaptor;

    @Test
    void createAccount() {
        User userCore = User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.infrastructure.persistence.User userInfra = dev.samuel.financesystem.infrastructure.persistence.User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account accountCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account accountInfra = Account.builder()
                .id(accountCore.id())
                .user(userInfra)
                .balance(accountCore.balance())
                .agency(accountCore.agency())
                .number(accountCore.number())
                .createdAt(accountCore.createdAt())
                .build();

        Mockito.when(accountMapper.toPersistenceEntity(accountCore)).thenReturn(accountInfra);
        Mockito.when(accountRepository.save(accountInfra)).thenReturn(accountInfra);
        Mockito.when(accountMapper.toDomain(accountInfra)).thenReturn(accountCore);

        accountGateway.createAccount(accountCore);

        Mockito.verify(accountMapper).toPersistenceEntity(accountCore);
        Mockito.verify(accountMapper).toDomain(accountInfra);
        Mockito.verify(accountRepository).save(accountInfra);
        Mockito.verify(accountRepository).save(argumentCaptor.capture());
    }

    @Test
    void existsByUserId() {
        User userCore = User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account accountCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(accountRepository.existsByUserId(accountCore.user().id())).thenReturn(true);

        accountGateway.existsByUserId(accountCore.user().id());

        Mockito.verify(accountRepository).existsByUserId(accountCore.user().id());
    }

    @Test
    void findByUserId() {
        User userCore = User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.infrastructure.persistence.User userInfra = dev.samuel.financesystem.infrastructure.persistence.User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account accountCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account accountInfra = Account.builder()
                .id(accountCore.id())
                .user(userInfra)
                .balance(accountCore.balance())
                .agency(accountCore.agency())
                .number(accountCore.number())
                .createdAt(accountCore.createdAt())
                .build();

        Mockito.when(accountRepository.findByUserIdWithUser(accountCore.user().id())).thenReturn(Optional.of(accountInfra));
        Mockito.when(accountMapper.toDomain(accountInfra)).thenReturn(accountCore);

        accountGateway.findByUserId(accountCore.user().id());

        Mockito.verify(accountRepository).findByUserIdWithUser(accountCore.user().id());
        Mockito.verify(accountMapper).toDomain(accountInfra);
    }

    @Test
    void getBalance() {
        User user = User.builder()
                .id(1L)
                .build();

        dev.samuel.financesystem.infrastructure.persistence.User userInfra = dev.samuel.financesystem.infrastructure.persistence.User.builder()
                .id(1L)
                .build();

        dev.samuel.financesystem.core.entities.Account accountCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(user)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account accountInfra = Account.builder()
                .id(accountCore.id())
                .user(userInfra)
                .balance(accountCore.balance())
                .agency(accountCore.agency())
                .number(accountCore.number())
                .createdAt(accountCore.createdAt())
                .build();

        Mockito.when(accountRepository.findByUserIdWithUser(accountCore.user().id())).thenReturn(Optional.of(accountInfra));
        Mockito.when(accountMapper.toDomain(accountInfra)).thenReturn(accountCore);

        accountGateway.findByUserId(accountCore.user().id());

        Mockito.verify(accountRepository).findByUserIdWithUser(accountCore.user().id());
        Mockito.verify(accountMapper).toDomain(accountInfra);
    }
}