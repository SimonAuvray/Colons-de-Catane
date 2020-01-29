package fr.colonscatane.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import fr.colonscatane.dao.IDAOCoin;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOSegment;
import fr.colonscatane.exception.IsNotCoinException;
import fr.colonscatane.exception.IsNotRouteVoisineException;
import fr.colonscatane.exception.IsNotSegmentException;
import fr.colonscatane.exception.IsOccupeException;
import fr.colonscatane.exception.IsVoisinTropProcheException;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.service.CoinService;
import fr.colonscatane.service.SegmentService;
import fr.colonscatane.service.SseService;
import fr.colonscatane.views.Views;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/partie")
public class PartieRestController {

	@Autowired
	private IDAOJoueur daoJoueur;

	@Autowired
	private IDAOCoin daoCoin;

	@Autowired
	private IDAOSegment daoSegment;

	@Autowired
	private CoinService serviceCoin;

	@Autowired
	private SegmentService serviceSegment;

	@Autowired
	private SseService sse;

	@GetMapping("/coin/{x}/{y}")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public Coin enregistrerPremiereColonie(@PathVariable int x, @PathVariable int y, HttpSession session) {
		Joueur j = daoJoueur.findById((Integer) session.getAttribute("userId")).orElse(null);
		j.setCouleur(Couleur.BLEU);
		Coin result = null;
		try {
			result = serviceCoin.placerPremiereColonie(x, y, j);
			sse.emissionObjet(result);
			result.setOccupation(j);
		} catch (IsVoisinTropProcheException | IsOccupeException | IsNotCoinException e) {
			sse.emissionObjet(e.getMessage());
		}
		return result;
	}

	@GetMapping("/segment/{x}/{y}")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public Segment enregistrerRoute(@PathVariable(value = "x") int x, @PathVariable(value = "y") int y,
			HttpSession session) {
		System.out.println("Enregistrement d'une route");
		Joueur j = daoJoueur.findById((Integer) session.getAttribute("userId")).orElse(null);
		j.setCouleur(Couleur.BLEU);
		Segment result = null;
		try {
			result = serviceSegment.placerUneRoute(x, y, j);
			result.setOccupation(j);
			sse.emissionObjet(result);
		} catch (IsNotSegmentException | IsOccupeException | IsNotRouteVoisineException e) {
			sse.emissionObjet(e.getMessage());
		}
		
		return result;
	}

	@GetMapping("/listeCoins")
	@JsonView(Views.Coin.class)
	public List<Coin> getListeCoins() {
		return daoCoin.findAll();
	}

	@GetMapping("/listeSegments")
	@JsonView(Views.Segment.class)
	public List<Segment> getListeSegments() {
		return daoSegment.findAll();
	}
}
