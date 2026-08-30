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
        Account origin = Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account destination = Account.builder()
                .id(2L)
                .userId(2L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Transaction transactionCore = dev.samuel.financesystem.core.entities.Transaction.builder()
                .id(1L)
                .originId(origin.getId())
                .destinationId(destination.getId())
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionInfra = Transaction.builder()
                .id(1L)
                .originId(origin.getId())
                .destinationId(destination.getId())
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        User user = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(accountRepository.findById(origin.getId())).thenReturn(Optional.of(origin));
        Mockito.when(accountRepository.findById(destination.getId())).thenReturn(Optional.of(destination));
        Mockito.when(accountRepository.save(origin)).thenReturn(origin);
        Mockito.when(accountRepository.save(destination)).thenReturn(destination);
        Mockito.when(transactionMapper.toPersistenceEntity(transactionCore)).thenReturn(transactionInfra);
        Mockito.when(transactionRepository.save(transactionInfra)).thenReturn(transactionInfra);
        Mockito.when(userRepository.findById(destination.getUserId())).thenReturn(Optional.of(user));

        transactionGateway.transfer(transactionCore);

        Mockito.verify(accountRepository).findById(origin.getId());
        Mockito.verify(accountRepository).findById(destination.getId());
        Mockito.verify(accountRepository).save(origin);
        Mockito.verify(accountRepository).save(destination);
        Mockito.verify(transactionMapper).toPersistenceEntity(transactionCore);
        Mockito.verify(transactionRepository).save(transactionInfra);
        Mockito.verify(userRepository).findById(destination.getId());
        Mockito.verify(userProducer).publishEvent(Mockito.any(), Mockito.any());
        Mockito.verify(transactionMapper).toDomain(transactionInfra);
    }

    @Test
    void findByAccountId() {
        Account origin = Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account destination = Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.Transaction transactionCore = dev.samuel.financesystem.core.entities.Transaction.builder()
                .id(1L)
                .originId(origin.getId())
                .destinationId(destination.getId())
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionInfra = Transaction.builder()
                .id(1L)
                .originId(origin.getId())
                .destinationId(destination.getId())
                .amount(BigDecimal.TEN)
                .type(Type.TRANSFER)
                .status(Status.COMPLETED)
                .description("Description Test")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(transactionRepository.findByOriginIdOrDestinationId(transactionInfra.getOriginId(), transactionInfra.getDestinationId())).thenReturn(List.of(transactionInfra));
        Mockito.when(transactionMapper.toDomain(transactionInfra)).thenReturn(transactionCore);

        transactionGateway.findByAccountId(origin.getId());

        Mockito.verify(transactionRepository).findByOriginIdOrDestinationId(transactionInfra.getOriginId(), transactionInfra.getDestinationId());
        Mockito.verify(transactionMapper).toDomain(transactionInfra);
    }
}