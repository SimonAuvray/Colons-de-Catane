package fr.colonscatane.controller;



import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.colonscatane.application.PartieContextLoader;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOPartie;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.ROLE;
import fr.colonscatane.modele.TuileRessource;



@Controller
public class PartieController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	@Autowired
	private IDAOPartie daoPartie;
	@Autowired
	private IDAOTuileRessource daoTuile;
	@Autowired
	private PartieContextLoader partieContext;
	
	@GetMapping("/partie")
	public String lancerpartie(Model model) {
		
		List<TuileRessource> mesTuiles =  daoTuile.findAll();
		
		for (TuileRessource t : mesTuiles) {
			model.addAttribute("Tuile"+t.getId(), t);
		}
		
//		model.addAttribute("joueurs", partieContext.getParties().get(0).getLstJoueurs());
		
		return "partie";
	}
	
	@GetMapping("/menu/retour")
	@Transactional
	public String quitterPartie(HttpSession session) {
		Joueur joueurUtilisateur = daoJoueur.findByUsername((String) session.getAttribute("user")).orElse(null);
		joueurUtilisateur.resetJoueur();
		daoJoueur.save(joueurUtilisateur);
		return "menu";
	}

	@PostMapping("/nouvellepartie")
	@Transactional
	public String ajoutJoueur(Model model,
			HttpSession session,
			@RequestParam String username
			) {
		
		//recuperation de la partie a lancer
		Joueur joueurUtilisateur = daoJoueur.findByUsername((String) session.getAttribute("user")).orElse(null) ;
		Partie maPartie = daoPartie.findByIdFetchingJoueurs(joueurUtilisateur.getPartie().getId());
		//recuperation du joueur invite
		Joueur joueurInvite = daoJoueur.findByUsername(username).orElse(null);
		
		if (maPartie.getLstJoueurs().size() >= 4) {
			JOptionPane.showMessageDialog(null, "Vous avez atteint le nombre maximal de joueurs");
		} else {
			
			if(joueurInvite == null) {
				JOptionPane.showMessageDialog(null, "Ce joueur n'est pas inscrit a Colon de Catane");
			}
			else {
				if (joueurInvite.getRole().toString().equals("Joueur")) {
					JOptionPane.showMessageDialog(null, "Ce joueur est deja dans une partie");
				}
				else {
					//ajout de l'invite a la partie quand il accepte
					joueurInvite.setRole(ROLE.Invite);
					daoJoueur.save(joueurInvite);
				}
			}
		}		
		return "nouvellepartie";
	}
}

