package dev.samuel.financesystem.infrastructure.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        Contact contact = new Contact();
        contact.name("Samuel");
        contact.email("samuelmarques@gmail.com");

        Info info = new Info();
        info.title("auth-service");
        info.version("v1");
        info.description("Aplicação para gerenciamento de contas e transações financeiras.");
        info.contact(contact);

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));

    }
}
