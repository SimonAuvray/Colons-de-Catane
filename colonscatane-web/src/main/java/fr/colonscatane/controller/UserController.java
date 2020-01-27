package fr.colonscatane.controller;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ListeRoles;

@Controller
public class UserController {
	
	@Autowired
	IDAOUtilisateur daoUtilisateur;
	
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
	
	@GetMapping("/deleteuser")
	public String delete(HttpSession session) {
		daoUtilisateur.deleteById( (Integer) session.getAttribute("userId") );
		session.invalidate();
		return "redirect:home";
	}
	
	@GetMapping("/deconnect")
	public String deconnect(HttpSession session) {
		session.invalidate();
		return "redirect:home";
	}
	
}
