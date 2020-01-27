package fr.colonscatane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOSegment;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Segment;

@Service
public class SegmentService {

	@Autowired
	private IDAOSegment daoSegment;
	
	@Autowired
	private IDAOJoueur daoJoueur;

	/**
	 * Enregistre l'occupation du joueur sur une route
	 * @param x
	 * @param y
	 * @param j
	 * @return
	 */
	public boolean placerUneRoute(int x, int y, Joueur j) {
		boolean segmentOk = true;
		if (isSegment(x, y)) {
			if (isLibre(x, y)) {
				if (isRouteVoisineColonie(x, y, j)) {
					Segment laRouteSelectionne = (Segment) daoSegment.findByXAndY(x, y).orElse(null);
					laRouteSelectionne.setOccupation(j);
					daoSegment.save(laRouteSelectionne);
				} else {
					System.out.println("ERR : Veuillez saisir une route");
					segmentOk = false;
				}
			} else {
				System.out.println("ERR : Cette route est déjà occupée");
				segmentOk = false;
			}
		} else {
			System.out.println("ERR : La route n'est pas voisine d'une colonie du joueur");
			segmentOk = false;
		}
		return segmentOk;
	}

	/**
	 * Verifie si les coordonnees correspondent a un segment
	 * 
	 * @param xColonie int
	 * @param yColonie int
	 * @return boolean
	 */
	private boolean isSegment(int x, int y) {
		System.out.println("INF : Verification de la validite du segment");
		return ((Segment) daoSegment.findByXAndY(x, y).orElse(null) != null);
	}
	
	/**
	 * Vï¿½rifie si le coin est libre de tout joueur
	 * 
	 * @param xColonie int
	 * @param yColonie int
	 * @return boolean
	 */
	private boolean isLibre(int x, int y) {
		System.out.println("INF : Verification de la libre occupation de la route");
		Segment segment = (Segment) daoSegment.findByXAndY(x, y).orElse(null);
		return (segment.getOccupation() == null);
	}
	
	/**
	 * Vérifie si une des colonies du joueur est voisine de la route sélectionnée
	 * @param x int
	 * @param y int
	 * @param j Joueur
	 * @return boolean
	 */
	private boolean isRouteVoisineColonie(int x, int y, Joueur j) {
		boolean routeVoisine = false;
		j = daoJoueur.findByIdFetchingPosition(j.getId()).orElse(null);
		if(j!= null) {
			long nbColonieVoisine = j.getCoins()
			.stream()
			.filter(c -> (c.getX() == (x - 1) || 
			c.getX() == (x + 1) ||
			c.getY() == (y-1) ||
			c.getY() == (y+1)) ).count();
			if(nbColonieVoisine >= 1) {
				routeVoisine = true;
			}
		}
		return routeVoisine;
	}
}
