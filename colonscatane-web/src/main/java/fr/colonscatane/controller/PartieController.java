package fr.colonscatane.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;
import fr.colonscatane.modele.TuileRessource;

@Controller
public class PartieController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOTuileRessource daoTuile;
	
	
	
	@GetMapping("/partie")
	public String lancerpartie(Model model) {
		
		List<TuileRessource> mesTuiles =  daoTuile.findAll();
		
		for (TuileRessource t : mesTuiles) {
			model.addAttribute("Tuile"+t.getId(), t);
			
			//System.out.println( "RessourceTuile"+t.getId() +" "+t.getType().toString());
		}
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
