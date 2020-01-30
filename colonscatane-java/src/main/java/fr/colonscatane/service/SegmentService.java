package fr.colonscatane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOSegment;
import fr.colonscatane.exception.IsNotRouteVoisineException;
import fr.colonscatane.exception.IsNotSegmentException;
import fr.colonscatane.exception.IsOccupeException;
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
	 * @throws IsNotSegmentException 
	 * @throws IsOccupeException
	 * @throws IsNotRouteVoisineException 
	 */
	public Segment placerUneRoute(int x, int y, Joueur j) throws IsNotSegmentException, IsOccupeException, IsNotRouteVoisineException {
		Segment laRouteSelectionne = null;
		if (isSegment(x, y)) {
			if (isLibre(x, y)) {
				if (isRouteVoisineColonie(x, y, j)) {
					laRouteSelectionne = daoSegment.findByXAndY(x, y).orElse(null);
					laRouteSelectionne.setOccupation(j);
					laRouteSelectionne = daoSegment.save(laRouteSelectionne);
				} else {
					throw new IsNotRouteVoisineException();
				}
			} else {
				throw new IsOccupeException();
			}
		} else {
			throw new IsNotSegmentException();
		}
		return laRouteSelectionne;
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
