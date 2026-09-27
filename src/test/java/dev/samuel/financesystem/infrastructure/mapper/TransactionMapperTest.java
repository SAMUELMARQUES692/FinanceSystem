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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionMapperTest {

    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void toPersistenceEntity() {
        Transaction transactionCore = Transaction.builder()
                .id(1L)
                .origin(Account.builder()
                        .id(1L)
                        .build())
                .destination(Account.builder()
                        .id(2L)
                        .build())
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Transaction transactionInfra = mapper.toPersistenceEntity(transactionCore);

        assertNotNull(transactionInfra);

        assertEquals(transactionCore.id(), transactionInfra.getId());
        assertEquals(transactionCore.origin().id(), transactionInfra.getOrigin().getId());
        assertEquals(transactionCore.destination().id(), transactionInfra.getDestination().getId());
        assertEquals(transactionCore.amount(), transactionInfra.getAmount());
        assertEquals(transactionCore.type(), transactionInfra.getType());
        assertEquals(transactionCore.status(), transactionInfra.getStatus());
        assertEquals(transactionCore.description(), transactionInfra.getDescription());
        assertEquals(transactionCore.createdAt(), transactionInfra.getCreatedAt());
    }

    @Test
    void toDomain() {
        dev.samuel.financesystem.infrastructure.persistence.Account origin = dev.samuel.financesystem.infrastructure.persistence.Account.builder()
                .id(1L)
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Account destination = dev.samuel.financesystem.infrastructure.persistence.Account.builder()
                .id(2L)
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Transaction transactionInfra = dev.samuel.financesystem.infrastructure.persistence.Transaction.builder()
                .id(1L)
                .origin(origin)
                .destination(destination)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transactionCore = mapper.toDomain(transactionInfra);

        assertNotNull(transactionCore);

        assertEquals(transactionInfra.getId(), transactionCore.id());
        assertEquals(transactionInfra.getOrigin().getId(), transactionCore.origin().id());
        assertEquals(transactionInfra.getDestination().getId(), transactionCore.destination().id());
        assertEquals(transactionInfra.getAmount(), transactionCore.amount());
        assertEquals(transactionInfra.getType(), transactionCore.type());
        assertEquals(transactionInfra.getStatus(), transactionCore.status());
        assertEquals(transactionInfra.getDescription(), transactionCore.description());
        assertEquals(transactionInfra.getCreatedAt(), transactionCore.createdAt());
    }

    @Test
    void toEntity() {
        TransactionRequest request = TransactionRequest.builder()
                .pix("pix-teste")
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
        Account origin = Account.builder()
                .id(1L)
                .build();

        Account destination = Account.builder()
                .id(2L)
                .build();


        Transaction transactionCore = Transaction.builder()
                .id(1L)
                .origin(origin)
                .destination(destination)
                .amount(BigDecimal.TEN)
                .type(Type.DEPOSIT)
                .status(Status.COMPLETED)
                .description("Descrição Teste")
                .createdAt(LocalDateTime.now())
                .build();

        TransactionResponse response = mapper.toTransactionResponse(transactionCore);

        assertNotNull(response);

        assertEquals(transactionCore.id(), response.id());
        assertEquals(transactionCore.origin().id(), response.origin().getId());
        assertEquals(transactionCore.destination().id(), response.destination().getId());
        assertEquals(transactionCore.amount(), response.amount());
        assertEquals(transactionCore.type(), response.type());
        assertEquals(transactionCore.status(), response.status());
        assertEquals(transactionCore.description(), response.description());
        assertEquals(transactionCore.createdAt(), response.createdAt());
    }
}
