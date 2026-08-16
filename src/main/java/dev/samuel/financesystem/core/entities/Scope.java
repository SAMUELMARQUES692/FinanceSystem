package dev.samuel.financesystem.core.entities;

public record Scope(
        Long id,
        String name
) {

    public static Builder builder() {
        return new Builder();
    }

   public static class Builder {
       private Long id;
       private String name;


       public Builder id(Long id) {
           this.id = id;
           return this;
       }

       public Builder name(String name) {
           this.name = name;
           return this;
       }

       public Scope build() {
           return new Scope(id, name);
       }

   }
}
