package fr.colonscatane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartieController {
	

	
	@GetMapping("/partie")
	public String lancerpartie() {
		return "partie";
	}
	
	

}