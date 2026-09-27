package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.enums.EmailStatus;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.persistence.Email;
import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.producer.UserProducer;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import dev.samuel.financesystem.infrastructure.repository.TransactionRepository;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionGatewayImplTest {

    @InjectMocks
    TransactionGatewayImpl transactionGateway;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionMapper transactionMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    UserProducer userProducer;

    @Test
    void transfer() {
        dev.samuel.financesystem.core.entities.User userCore = dev.samuel.financesystem.core.entities.User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account originCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account destinationCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        User userInfra = User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Account originInfra = Account.builder()
                .id(1L)
                .user(userInfra)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account destinationInfra = Account.builder()
                .id(1L)
                .user(userInfra)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Transaction transactionCore = dev.samuel.financesystem.core.entities.Transaction.builder()
                .id(1L)
                .origin(originCore)
                .destination(destinationCore)
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionInfra = Transaction.builder()
                .id(1L)
                .origin(originInfra)
                .destination(destinationInfra)
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(accountRepository.findById(originInfra.getId())).thenReturn(Optional.of(originInfra));
        Mockito.when(accountRepository.findById(destinationInfra.getId())).thenReturn(Optional.of(destinationInfra));
        Mockito.when(accountRepository.save(originInfra)).thenReturn(originInfra);
        Mockito.when(accountRepository.save(destinationInfra)).thenReturn(destinationInfra);
        Mockito.when(transactionMapper.toPersistenceEntity(transactionCore)).thenReturn(transactionInfra);
        Mockito.when(transactionRepository.save(transactionInfra)).thenReturn(transactionInfra);
        Mockito.when(userRepository.findById(destinationInfra.getUser().getId())).thenReturn(Optional.of(userInfra));

        transactionGateway.transfer(transactionCore);

        Mockito.verify(accountRepository).findById(originInfra.getId());
        Mockito.verify(accountRepository).findById(destinationInfra.getId());
        Mockito.verify(accountRepository).save(originInfra);
        Mockito.verify(accountRepository).save(destinationInfra);
        Mockito.verify(transactionMapper).toPersistenceEntity(transactionCore);
        Mockito.verify(transactionRepository).save(transactionInfra);
        Mockito.verify(userRepository).findById(destinationInfra.getId());
        Mockito.verify(userProducer).publishEvent(Mockito.any(), Mockito.any());
        Mockito.verify(transactionMapper).toDomain(transactionInfra);
    }

    @Test
    void findByAccountId() {
        dev.samuel.financesystem.core.entities.User userCore = dev.samuel.financesystem.core.entities.User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

       dev.samuel.financesystem.core.entities.Account originCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Account destinationCore = dev.samuel.financesystem.core.entities.Account.builder()
                .id(1L)
                .user(userCore)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        User userInfra = User.builder()
                .id(1L)
                .email("Email Teste")
                .name("Name Teste")
                .cpf("CPF Teste")
                .password("Password Teste")
                .createdAt(LocalDateTime.now())
                .build();

       Account originInfra = Account.builder()
                .id(1L)
                .user(userInfra)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account destinationInfra = Account.builder()
                .id(1L)
                .user(userInfra)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Transaction transactionCore = dev.samuel.financesystem.core.entities.Transaction.builder()
                .id(1L)
                .origin(originCore)
                .destination(destinationCore)
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionInfra = Transaction.builder()
                .id(1L)
                .origin(originInfra)
                .destination(destinationInfra)
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(transactionRepository.findByOriginIdOrDestinationId(transactionInfra.getOrigin().getId(), transactionInfra.getDestination().getId())).thenReturn(List.of(transactionInfra));
        Mockito.when(transactionMapper.toDomain(transactionInfra)).thenReturn(transactionCore);

        transactionGateway.findByAccountId(transactionInfra.getOrigin().getId());

        Mockito.verify(transactionRepository).findByOriginIdOrDestinationId(transactionInfra.getOrigin().getId(), transactionInfra.getDestination().getId());
        Mockito.verify(transactionMapper).toDomain(transactionInfra);
    }
}