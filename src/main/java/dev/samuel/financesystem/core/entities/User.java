package dev.samuel.financesystem.core.entities;

import java.time.LocalDateTime;
import java.util.List;

public record User(

        Long id,
        String name,
        String email,
        String password,
        LocalDateTime createdAt,
        List<Scope> scopes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private LocalDateTime createdAt;
        private List<Scope> scopes;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder scopes(List<Scope> scopes) {
            this.scopes = scopes;
            return this;
        }

        public User build() {
            return new User(id, name, email, password, createdAt, scopes);
        }

    }

}
