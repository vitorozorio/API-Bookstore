package br.com.SpringBookstore.SpringBookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Bookstore API")
                        .description("API para gerenciar livros, categorias e autores.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nome do Desenvolvedor")
                                .url("https://seusite.com")
                                .email("email@example.com"))
                        .license(new License()
                                .name("Licença")
                                .url("https://seusite.com/license")));
    }
}
