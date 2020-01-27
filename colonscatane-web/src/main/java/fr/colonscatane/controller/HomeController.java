package fr.colonscatane.controller;



import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.ApplicationScope;

import fr.colonscatane.config.AppConfig;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ListeRoles;

@Controller
@ApplicationScope
public class HomeController {
	
	@Autowired
	IDAOUtilisateur daoUtilisateur;

	
	@Autowired
	IDAOJoueur daoJoueur;
	
	@GetMapping("/home")
	public String home() {	
		return "home";
	}
	
	@PostMapping("/home")
	public String connexion(@Valid @ModelAttribute Joueur user,
			BindingResult result
			) {
		if(result.hasErrors()) {
			return "home";
		}
		if( daoUtilisateur.findByUsernameAndPassword(user.getUsername(), user.getPassword() ) == null ) {
			return "home";
		}
		else {
			return "redirect:menu";
		}
	}
	
	
	@GetMapping("/inscription")
	public String inscription() {
		return "inscription";
	}
	
	@PostMapping("/inscription")
	public String enregistrementUtilisateur (
			@Valid @ModelAttribute Joueur joueur,
			BindingResult result
			
			) {
		
		if (daoUtilisateur.findByUsername(joueur.getUsername()).isPresent()) {
			 
			 JOptionPane.showMessageDialog(null, "UTILISATEUR DEJA CREE"); 
			return "home"; 
		}
		
		if (result.hasErrors()) {
			 
			 JOptionPane.showMessageDialog(null, "Erreur"); 
			return "home"; 
		}
			
		else {
			
			joueur.setRole(ListeRoles.Inactif);
			daoUtilisateur.save(joueur);
			return "redirect:home";
			
		}
			
			
		
		
		
	}
			

}
