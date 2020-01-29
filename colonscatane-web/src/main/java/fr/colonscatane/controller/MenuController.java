package fr.colonscatane.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;
import fr.colonscatane.modele.Utilisateur;

@Controller
public class MenuController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOUtilisateur<Utilisateur> daoUtilisateur;
	
	@GetMapping("/menu")
	public String getMenu() {
		return "menu";
	}
	
	@GetMapping("/nouvellepartie")
	public String getNouvellePartie(Model model, HttpSession session) {
	
		List<Joueur> listeJoueurs = new ArrayList<Joueur>();			
		listeJoueurs = daoJoueur.findByRole(ROLE.Joueur).orElse(null);
		
		if (listeJoueurs != null) {
				int i = 1;
				for (Joueur j : listeJoueurs) {
					
				model.addAttribute("joueur" + i, j);
				i++;
				}
		}
		return "nouvellepartie";
	}
		
	
	@GetMapping("/parametres")
	public String getParametres() {
		return "parametres";
	}

}
