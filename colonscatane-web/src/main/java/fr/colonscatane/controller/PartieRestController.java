package fr.colonscatane.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import fr.colonscatane.application.PartieContextLoader;
import fr.colonscatane.dao.IDAOCoin;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOPartie;
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
import fr.colonscatane.service.PartieService;
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
	private IDAOPartie daoPartie;

	@Autowired
	private CoinService serviceCoin;

	@Autowired
	private SegmentService serviceSegment;
	
	@Autowired
	private PartieService servicePartie;

	@Autowired
	private SseService sse;
	
	@Autowired
	private PartieContextLoader partieContext;

	@GetMapping("/coin/{x}/{y}")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public Coin enregistrerPremiereColonie(@PathVariable int x, @PathVariable int y, HttpSession session) {
		Joueur j = daoJoueur.findById((Integer) session.getAttribute("userId")).orElse(null);
		CompletableFuture<Coin> result = null;
		Coin coin = null;
		try {
			result = serviceCoin.placerPremiereColonie(x, y, j);
			CompletableFuture.allOf(result);
			coin = result.get();
			coin.setOccupation(j);
		} catch (IsVoisinTropProcheException | IsOccupeException | IsNotCoinException | InterruptedException | ExecutionException e) {
			sse.emissionObjet(e.getMessage());
		}
		return coin;
	}

	@GetMapping("/segment/{x}/{y}")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public Segment enregistrerRoute(@PathVariable int x, @PathVariable int y,
			HttpSession session) {
		System.out.println("Enregistrement d'une route");
		Joueur j = daoJoueur.findById((Integer) session.getAttribute("userId")).orElse(null);
		CompletableFuture<Segment> result = null;
		Segment route = null;
		try {
			result = serviceSegment.placerUneRoute(x, y, j);
			CompletableFuture.allOf(result);
			route = result.get();
			route.setOccupation(j);
		} catch (IsNotSegmentException | IsOccupeException | IsNotRouteVoisineException | InterruptedException | ExecutionException e) {
			sse.emissionObjet(e.getMessage());
		}
		return route;
	}

	@GetMapping("/listeCoins")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public List<Coin> getListeCoins() {
		List<Coin> coins = daoCoin.findAll();
		return coins;
	}

	@GetMapping("/listeSegments")
	@JsonView(Views.PositionPlateauWithJoueur.class)
	public List<Segment> getListeSegments() {
		List<Segment> segments = daoSegment.findAll();
		return segments;
	}
	
	@GetMapping("/listeJoueurs")
	@JsonView(Views.Joueur.class)
	@Transactional
	public List<Joueur> getListejoueurs() {
		//pour l'instant une seule partie possible
		if( partieContext.getParties().size()>0) {
			return partieContext.getParties().get(0).getLstJoueurs();
		}else {
			return new ArrayList<Joueur>();
		}
	}
}
