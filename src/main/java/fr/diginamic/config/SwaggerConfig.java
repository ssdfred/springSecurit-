package fr.diginamic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
		.info(new Info()
		.title("API Recensement des villes")
		.version("1.0")
		.description("Cette API fournit des données de recensement de population pour la France.")
		.termsOfService("OPEN DATA")
		.contact(new Contact().name("Smée Frédéric").email("ssdfred@yahoo.fr").url("URL du contact"))
		.license(new License().name("Nom Frédéric").url("URL de la licence")));
		}
		}