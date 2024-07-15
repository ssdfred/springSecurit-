package fr.diginamic.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {

	@GetMapping("/")
	public String getIndex(Model model, Authentication authentication) {
		model.addAttribute("authentication", authentication);
		return "index";
	}

}
