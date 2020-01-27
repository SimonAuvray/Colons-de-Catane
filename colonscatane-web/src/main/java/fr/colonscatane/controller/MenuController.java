package fr.colonscatane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class MenuController {
	
	@GetMapping("/menu")
	public String getMenu() {
		return "menu";
	}
	

}
