package fr.colonscatane.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import fr.colonscatane.application.PartieContextLoader;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOPartie;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.ROLE;

@Controller
public class MenuController {

	@Autowired
	private IDAOJoueur daoJoueur;

	@Autowired
	private IDAOPartie daoPartie;

	@Autowired
	private PartieContextLoader partieContext;


	@GetMapping("/menu")
	public String getMenu() {
		return "menu";
	}

	@GetMapping("/nouvellepartie")
	@Transactional
	public String getNouvellePartie(Model model, HttpSession session) {
		
		 
			// enregistrer l'utilisateur de session comme joueur immediatement
			Joueur monJoueur = daoJoueur.findByUsername((String) session.getAttribute("user")).orElse(null);
			if(monJoueur == null) {
				return "redirect:home";
			}

			// creation de la partie, ajout de l'utilisateur (si pas deja fait) et
			// enregistrement de la nouvelle liste Joueurs
			if (monJoueur.getPartie() == null) {
				monJoueur.setRole(ROLE.Joueur);
				Partie maPartie = new Partie();
				List<Joueur> listeJoueurs = new ArrayList<Joueur>();
				listeJoueurs.add(monJoueur);
				maPartie.setLstJoueurs(listeJoueurs);
				maPartie = daoPartie.save(maPartie);
				monJoueur.setPartie(maPartie);
				monJoueur = daoJoueur.save(monJoueur);
				// enregistrement de la partie dans le scope application
				List<Partie> listeParties = new ArrayList<Partie>();
				listeParties.add(maPartie);
				partieContext.setParties(listeParties);
			}
			int i = 0;
			// recuperation du joueur mis a jour avec sa partie, pour chaque joueur de la
			// partie envoyer dans la session pour affichage
			Partie maPartie = daoPartie.findByIdFetchingJoueurs(monJoueur.getPartie().getId());
			if (partieContext.getParties() == null || partieContext.getParties().size()==0) {
				ArrayList<Partie> parties = new ArrayList<Partie>();
				parties.add(maPartie);
				partieContext.setParties(parties);
			}
//			Partie maPartie = daoPartie.findById(monJoueur.getPartie().getId()).get();
//			Hibernate.initialize(maPartie.getLstJoueurs());
			List<Joueur> mesJoueurs = maPartie.getLstJoueurs();
			for (Joueur j : mesJoueurs) {
				model.addAttribute("joueur" + i, j);
				i++;
			}
			
			
			return "nouvellepartie";
		
	}

	@GetMapping("/parametres")
	public String getParametres() {
		return "parametres";
	}
	
	@GetMapping("/copainscatane")
	public String getcopainscatane() {
		return "copainscatane";
	}

}
