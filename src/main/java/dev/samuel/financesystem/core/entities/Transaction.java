package dev.samuel.financesystem.core.entities;

import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Long id,
        Long originId,
        Long destinationId,
        BigDecimal amount,
        Type type,
        Status status,
        String description,
        LocalDateTime createdAt
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long originId;
        private Long destinationId;
        private BigDecimal amount;
        private Type type;
        private Status status;
        private String description;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder originId(Long originId) {
            this.originId = originId;
            return this;
        }

        public Builder destinationId(Long destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, originId, destinationId, amount, type, status, description, createdAt);
        }
    }
}
