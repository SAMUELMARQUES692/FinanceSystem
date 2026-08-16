package dev.samuel.financesystem.core.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Account(
        Long id,
        Long userId,
        BigDecimal balance,
        String agency,
        String number,
        LocalDateTime createdAt

) implements Serializable {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private BigDecimal balance;
        private String agency;
        private String number;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder agency(String agency) {
            this.agency = agency;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Account build() {
            return new Account(id, userId, balance, agency, number, createdAt);
        }
    }


}
