package fr.colonscatane.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.service.CoinService;
import fr.colonscatane.service.SegmentService;

@RestController
@CrossOrigin("*") 
@RequestMapping("/api/partie")
public class PartieRestController {

	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private CoinService serviceCoin;
	
	@Autowired
	private SegmentService serviceSegment;
	
	
	@GetMapping("/coin/{x}/{y}")
	public String enregistrerPremiereColonie(@PathVariable int x,
			@PathVariable int y,
			HttpSession session) {
		Joueur j = daoJoueur.findById((Integer)session.getAttribute("userId")).orElse(null);
		serviceCoin.placerPremiereColonie(x, y, j);
		return "redirect:partie";
	}
	
	@GetMapping("/partie/route/{x}/{y}")
	public String enregistrerRoute(@PathVariable(value="x") int x,
			@PathVariable(value="y") int y,
			HttpSession session) {
		System.out.println("Enregistrement d'une route");
		Joueur j = daoJoueur.findById((Integer)session.getAttribute("userId")).orElse(null);
		serviceSegment.placerUneRoute(x, y, j);
		return "redirect:partie";
	}
}
