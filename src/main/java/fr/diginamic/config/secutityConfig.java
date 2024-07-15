package fr.diginamic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import fr.diginamic.repository.UserAccountRepository;

@Configuration
@EnableMethodSecurity
public class secutityConfig {
	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	

}
