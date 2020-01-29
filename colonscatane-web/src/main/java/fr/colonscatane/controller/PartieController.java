package fr.colonscatane.controller;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;

@Controller
public class PartieController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@GetMapping("partie")
	public String lancerpartie() {
		return "partie";
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
}
