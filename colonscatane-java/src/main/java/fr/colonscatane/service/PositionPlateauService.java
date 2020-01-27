package fr.colonscatane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.colonscatane.dao.IDAOPositionPlateau;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.modele.TuileRessource;

@Service
public class PositionPlateauService {
	
	@Autowired
	private IDAOPositionPlateau<PositionPlateau> daoPositionPlateau;
	
	
	
	/**
	 * Initilisation du plateau La plateau et constitu� d'un ensemble d'hexagone
	 * format des routes et des coins
	 */
	public void initialisationPlateau() {

		// premiere moitiee de tableau
		// pour chaque ligne avec alternance coin/segment
		// on ne pose la tuile que au X,Y central (donc une case vide sur 2)

		int nonAchetable = 8;
		for (int i = 2; i < 7; i += 2) {
			for (int j = nonAchetable; j < 29 - nonAchetable; j++) {
				if (j % 2 == 0) {
					PositionPlateau positionPlateau = new Coin();
					positionPlateau.setX(i);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				} else {
					PositionPlateau positionPlateau = new Segment();
					positionPlateau.setX(i);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				}
			}
			nonAchetable -= 2;
		}
		// pour chaque "ligne de tuile"
		int compteurTuile = -1;
		nonAchetable = 8;
		for (int i = 3; i < 8; i += 2) {
			for (int j = nonAchetable; j < 29 - nonAchetable; j++) {
				int starter;
				if (i == 5) {
					starter = j + 2;
				} else {
					starter = j;
				}
				if (starter % 4 == 0) {
					PositionPlateau positionPlateau = new Segment();
					positionPlateau.setX(i);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				} else {
					// c'est une tuile
					if (compteurTuile % 3 == 0) {
						PositionPlateau positionPlateau = new TuileRessource();
						positionPlateau.setX(i);
						positionPlateau.setY(j);
						daoPositionPlateau.save(positionPlateau);
					}
					compteurTuile += 1;
				}
			}
			nonAchetable -= 2;
		}

		// pour la "descente" du plateau
		nonAchetable = 4;
		for (int i = 8; i < 13; i += 2) {
			for (int j = nonAchetable; j < 29 - nonAchetable; j++) {
				if (j % 2 == 0) {
					PositionPlateau positionPlateau = new Coin();
					positionPlateau.setX(i);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				} else {
					PositionPlateau positionPlateau = new Segment();
					positionPlateau.setX(i);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				}
			}
			nonAchetable += 2;
		}

		// ligne de tuile 9
		compteurTuile = -1;
		for (int j = 6; j < 23; j++) {
			int starter = j + 2;
			if (starter % 4 == 0) {
				PositionPlateau positionPlateau = new Segment();
				positionPlateau.setX(9);
				positionPlateau.setY(j);
				daoPositionPlateau.save(positionPlateau);
			} else {
				// c'est une tuile
				if (compteurTuile % 3 == 0) {
					PositionPlateau positionPlateau = new TuileRessource();
					positionPlateau.setX(9);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				}
				compteurTuile += 1;
			}
		}

		// ligne de tuile 11
		compteurTuile = -1;
		for (int j = 8; j < 21; j++) {
			if (j % 4 == 0) {
				PositionPlateau positionPlateau = new Segment();
				positionPlateau.setX(11);
				positionPlateau.setY(j);
				daoPositionPlateau.save(positionPlateau);
			} else {
				// c'est une tuile
				if (compteurTuile % 3 == 0) {
					PositionPlateau positionPlateau = new TuileRessource();
					positionPlateau.setX(11);
					positionPlateau.setY(j);
					daoPositionPlateau.save(positionPlateau);
				}
				compteurTuile += 1;
			}
		}
	}
	
	
}
