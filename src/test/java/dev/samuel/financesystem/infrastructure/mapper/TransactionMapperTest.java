package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
import dev.samuel.financesystem.infrastructure.response.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void toPersistenceEntity() {
        Transaction transactionCore = Transaction.builder()
                .id(1L)
                .originId(1L)
                .destinationId(1L)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Transaction transactionInfra = mapper.toPersistenceEntity(transactionCore);

        assertNotNull(transactionInfra);

        assertEquals(transactionCore.id(), transactionInfra.getId());
        assertEquals(transactionCore.originId(), transactionInfra.getOriginId());
        assertEquals(transactionCore.destinationId(), transactionInfra.getDestinationId());
        assertEquals(transactionCore.amount(), transactionInfra.getAmount());
        assertEquals(transactionCore.type(), transactionInfra.getType());
        assertEquals(transactionCore.status(), transactionInfra.getStatus());
        assertEquals(transactionCore.description(), transactionInfra.getDescription());
        assertEquals(transactionCore.createdAt(), transactionInfra.getCreatedAt());
    }

    @Test
    void toDomain() {
        dev.samuel.financesystem.infrastructure.persistence.Transaction transactionInfra = dev.samuel.financesystem.infrastructure.persistence.Transaction.builder()
                .id(1L)
                .originId(1L)
                .destinationId(1L)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionCore = mapper.toDomain(transactionInfra);

        assertNotNull(transactionCore);

        assertEquals(transactionInfra.getId(), transactionCore.id());
        assertEquals(transactionInfra.getOriginId(), transactionCore.originId());
        assertEquals(transactionInfra.getDestinationId(), transactionCore.destinationId());
        assertEquals(transactionInfra.getAmount(), transactionCore.amount());
        assertEquals(transactionInfra.getType(), transactionCore.type());
        assertEquals(transactionInfra.getStatus(), transactionCore.status());
        assertEquals(transactionInfra.getDescription(), transactionCore.description());
        assertEquals(transactionInfra.getCreatedAt(), transactionCore.createdAt());
    }

    @Test
    void toEntity() {
        TransactionRequest request = TransactionRequest.builder()
                .destinationId(1L)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .description("Descrição Teste")
                .build();

        Transaction transactionCore = mapper.toEntity(request);

        assertNotNull(transactionCore);

        assertEquals(request.amount(), transactionCore.amount());
        assertEquals(request.type(), transactionCore.type());
        assertEquals(request.description(), transactionCore.description());
    }

    @Test
    void toTransactionResponse() {
        Transaction transactionCore = Transaction.builder()
                .id(1L)
                .originId(1L)
                .destinationId(1L)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        TransactionResponse response = mapper.toTransactionResponse(transactionCore);

        assertNotNull(response);

        assertEquals(transactionCore.id(), response.id());
        assertEquals(transactionCore.originId(), response.originId());
        assertEquals(transactionCore.destinationId(), response.destinationId());
        assertEquals(transactionCore.amount(), response.amount());
        assertEquals(transactionCore.type(), response.type());
        assertEquals(transactionCore.status(), response.status());
        assertEquals(transactionCore.description(), response.description());
        assertEquals(transactionCore.createdAt(), response.createdAt());
    }
}