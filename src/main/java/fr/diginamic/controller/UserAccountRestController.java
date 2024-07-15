package fr.diginamic.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.dto.UserAccountDto;
import fr.diginamic.entities.UserAccount;
import fr.diginamic.mappers.UserAccountMapper;
import fr.diginamic.service.UserAccountService;

@RestController
@RequestMapping("/users")

public class UserAccountRestController {

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    @GetMapping
    public List<UserAccountDto> getAllUsers() {
        return userAccountService.findAll().stream()
                .map(UserAccountMapper::toDto)
                .collect(Collectors.toList());
    }
	
	@PostMapping
    public ResponseEntity<UserAccountDto> createUser(@RequestBody UserAccountDto userAccountDto) {
        userAccountDto.setPassword(passwordEncoder.encode(userAccountDto.getPassword()));
        UserAccount userAccount = UserAccountMapper.toEntity(userAccountDto);
        UserAccount createdUser = userAccountService.saveUserAccount(userAccount);
        return ResponseEntity.ok(UserAccountMapper.toDto(createdUser));
    }
}
