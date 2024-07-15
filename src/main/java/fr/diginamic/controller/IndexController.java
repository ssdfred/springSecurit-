package fr.diginamic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
//	@GetMapping("/townList")
//	public String getVille() {
//		return "town/townList";
//	}
}
