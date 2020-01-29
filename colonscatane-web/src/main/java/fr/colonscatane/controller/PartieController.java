package fr.colonscatane.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.service.CoinService;
import fr.colonscatane.service.SegmentService;

@Controller
public class PartieController {
	

	
	@GetMapping("/partie")
	public String lancerpartie() {
		return "partie";
	}
	
	

}