package fr.colonscatane.controller;



import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Utilisateur;
import fr.colonscatane.service.SseService;

@Controller
@ApplicationScope
public class HomeController {
	
	@Autowired
	IDAOUtilisateur<Utilisateur> daoUtilisateur;

	
	@Autowired
	IDAOJoueur daoJoueur;
	
	@Autowired
	private SseService sseService;
	
	@GetMapping("/home")
	public String home() {	
		return "home";
	}
	
	@PostMapping("/home")
	public String connexion(@Valid @ModelAttribute Joueur user,
			BindingResult result,
			HttpSession session
			) {
		if(result.hasErrors()) {
			return "home";
		}
		if( daoUtilisateur.findByUsernameAndPassword(user.getUsername(), user.getPassword() ).orElse(null) == null ) {
			return "home";
		}
		else {
			int monId = daoUtilisateur.findByUsername(user.getUsername()).get().getId();
			session.setAttribute("user", user.getUsername());
			session.setAttribute("userId", monId);
			return "redirect:menu";
		}
	}
	
	
	@GetMapping("/inscription")
	public String inscription() {
		return "inscription";
	}	
	
	@GetMapping("/sse")
	public SseEmitter addEmitter() {
		return sseService.gestionEmitters();
	}

}
