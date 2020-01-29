package fr.colonscatane.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;

@Controller
public class MenuController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
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
	
	@PostMapping("/nouvellepartie")
	public String ajoutJoueur(
			@RequestParam String nom
			) {
		
		if (daoJoueur.findByRole(ROLE.Joueur).get().size() >= 4) {
			 JOptionPane.showMessageDialog(null, "Vous avez atteint le nombre maximal de joueurs");
			return "nouvellepartie";
		}
		
		else {	
			
			Optional<Joueur> monJoueur = daoJoueur.findByNom(nom);
			monJoueur.get().setRole(ROLE.Joueur);
			daoJoueur.save(monJoueur.get());
		}
		return "redirect:nouvellepartie";
	}
		
	
	@GetMapping("/parametres")
	public String getParametres() {
		return "parametres";
	}

}
