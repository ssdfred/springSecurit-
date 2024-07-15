package fr.diginamic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import fr.diginamic.dto.UserAccountDto;
import fr.diginamic.entities.UserAccount;
import fr.diginamic.mappers.UserAccountMapper;
import fr.diginamic.service.UserAccountService;

@Controller
@RequestMapping("/users")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	   @GetMapping("/create")
	    public String showCreateForm(Model model) {
	        model.addAttribute("user", new UserAccountDto());
	        return "user/create";
	    }

	    @PostMapping("/create")
	    public String createUser(@ModelAttribute UserAccountDto userAccountDto) {
	        userAccountDto.setPassword(passwordEncoder.encode(userAccountDto.getPassword()));
	        UserAccount userAccount = UserAccountMapper.toEntity(userAccountDto);
	        userAccountService.saveUserAccount(userAccount);
	        return "redirect:/users";
	    }
	
}
