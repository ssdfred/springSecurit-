package fr.diginamic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.diginamic.entities.UserAccount;
import fr.diginamic.mappers.UserAccountMapper;
import fr.diginamic.repository.UserAccountRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    UserDetailsService userDetailsService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
    	return username ->  UserAccountMapper.toUserDetails(userAccountRepository.findByUsername(username));
    }
    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
		this.passwordEncoder = passwordEncoder;
    }
    
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }


    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @PostConstruct
    public void init() {
    	createUser ("user", "user", "ROLE_USER");
    	createUser ("admin", "admin", "ROLE_ADMIN");
    	}
    public UserAccount createUser(String username, String password, String... roles) {
        if (userAccountRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        UserAccount userAccount = new UserAccount(username, encodedPassword, roles);
        return userAccountRepository.save(userAccount);
    }
    public UserAccount saveUserAccount(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    
}
