package fr.diginamic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.diginamic.repository.UserAccountRepository;

@Configuration
@EnableMethodSecurity
public class secutityConfig {
	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/", "login").permitAll()
				.requestMatchers("/logout").authenticated()
				.requestMatchers("/townList").authenticated()
				.requestMatchers("/deleteTown/**").hasRole("ADMIN")
				.anyRequest().denyAll())
				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
		return http.build();
	}

}
