package fr.colonscatane.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/nouvellepartie")
public class MenuRestController {
	
	private List<SseEmitter> emitters = new ArrayList<SseEmitter>();
	
	
	@PostMapping("/nouvellepartie")
	public void send() {
	
	}
	
	

}
