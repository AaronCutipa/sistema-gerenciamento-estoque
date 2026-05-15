package school.sptech.sistema_gerenciamento_estoque.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SweggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gerenciamento de Estoque")
                        .version("1.0")
                        .description("Documentação da API do sistema de estoque")
                        .contact(new Contact()
                                .name("Aaron Cutipa Canaviri")
                                .email("aaron.canaviri@sptech.school")
                                .url("https://github.com/AaronCutipa/sistema-gerenciamento-estoque.git")));
    }
}
