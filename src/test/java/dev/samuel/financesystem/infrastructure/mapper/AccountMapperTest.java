package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void toPersistenceEntity() {
        Account accountCore = Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Account accountInfra = mapper.toPersistenceEntity(accountCore);

        assertNotNull(accountInfra);

        assertEquals(accountCore.id(), accountInfra.getId());
        assertEquals(accountCore.userId(), accountInfra.getUserId());
        assertEquals(accountCore.balance(), accountInfra.getBalance());
        assertEquals(accountCore.agency(), accountInfra.getAgency());
        assertEquals(accountCore.number(), accountInfra.getNumber());
        assertEquals(accountCore.createdAt(), accountInfra.getCreatedAt());
    }

    @Test
    void toDomain() {
        dev.samuel.financesystem.infrastructure.persistence.Account accountInfra = dev.samuel.financesystem.infrastructure.persistence.Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        Account accountCore = mapper.toDomain(accountInfra);

        assertNotNull(accountCore);

        assertEquals(accountInfra.getId(), accountCore.id());
        assertEquals(accountInfra.getUserId(), accountCore.userId());
        assertEquals(accountInfra.getBalance(), accountCore.balance());
        assertEquals(accountInfra.getAgency(), accountCore.agency());
        assertEquals(accountInfra.getNumber(), accountCore.number());
        assertEquals(accountInfra.getCreatedAt(), accountCore.createdAt());
    }

    @Test
    void toEntity() {
        AccountRequest request = AccountRequest.builder()
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .build();

        Account accountCore = mapper.toEntity(request);

        assertNotNull(accountCore);

        assertEquals(request.balance(), accountCore.balance());
        assertEquals(request.agency(), accountCore.agency());
        assertEquals(request.number(), accountCore.number());
    }

    @Test
    void toAccountResponse() {
        Account accountCore = Account.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.TEN)
                .agency("123123213")
                .number("23")
                .createdAt(LocalDateTime.now())
                .build();

        AccountResponse response = mapper.toAccountResponse(accountCore);

        assertNotNull(response);

        assertEquals(accountCore.id(), response.id());
        assertEquals(accountCore.userId(), response.userId());
        assertEquals(accountCore.balance(), response.balance());
        assertEquals(accountCore.agency(), response.agency());
        assertEquals(accountCore.number(), response.number());
        assertEquals(accountCore.createdAt(), response.createdAt());

    }
}