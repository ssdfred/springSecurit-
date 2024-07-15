package fr.diginamic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TownController {

	@GetMapping("/townList")
	public String getVille() {
		return "town/townList";
	}
}
