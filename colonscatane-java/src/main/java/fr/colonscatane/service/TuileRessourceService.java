package fr.colonscatane.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypePosition;
import fr.colonscatane.modele.TypeTuile;

@Service
public class TuileRessourceService {

	@Autowired
	private IDAOTuileRessource daoTuileRessource;
	
	/**
	 * Attribution d'un type de ressources � chaque tuile
	 */
	public void placementRessource() {

		List<TuileRessource> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);

		Collections.shuffle(mesTuiles);

		int compteurPrairie = 4;
		int compteurForet = 4;
		int compteurChamp = 4;
		int compteurMontagne = 3;
		int compteurCarriere = 3;

		int parcoureur = 0;

		try {

			for (int i = 0; i < compteurPrairie; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Prairie);
				parcoureur++;
				daoTuileRessource.save(maTuile);
			}

			for (int i = 0; i < compteurForet; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Foret);
				parcoureur++;
				daoTuileRessource.save(maTuile);
			}

			for (int i = 0; i < compteurChamp; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Champ);
				parcoureur++;
				daoTuileRessource.save(maTuile);
			}

			for (int i = 0; i < compteurMontagne; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Montagne);
				parcoureur++;
				daoTuileRessource.save(maTuile);
			}

			for (int i = 0; i < compteurCarriere; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Carriere);
				parcoureur++;
				daoTuileRessource.save(maTuile);
			}

			TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
			maTuile.setType(TypeTuile.Desert);
			daoTuileRessource.save(maTuile);
		}

		catch (Exception e) {
			System.out.println("Erreur dans placementRessource");
			e.printStackTrace();
		}

	}
	
	/**
	 * Attribution d'un nombre � chaque tuile ressource
	 */
	public void placementNumero() {

		List<TuileRessource> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);

		Collections.shuffle(mesTuiles);

		List<TuileRessource> ListDesert = daoTuileRessource.findByTypeRessource(TypeTuile.Desert);

		TuileRessource desert = ListDesert.get(0);

		desert.setNumero(0);
		try {
			daoTuileRessource.save(desert);
		} catch (Exception e) {
			System.out.println("erreur desertique");
			e.printStackTrace();
		}

		try {
			mesTuiles.removeIf(t -> t.getId() == desert.getId());

		} catch (Exception e) {
			System.out.println("erreur desertique");
			e.printStackTrace();
		}

		int parcoureur = 0;
		int numeroTuile = 2;

		try {

			TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
			maTuile.setNumero(numeroTuile);
			parcoureur++;

			daoTuileRessource.save(maTuile);

			for (numeroTuile = 3; numeroTuile < 7; numeroTuile++) {
				for (int j = 0; j < 2; j++) {
					maTuile = (TuileRessource) mesTuiles.get(parcoureur);
					maTuile.setNumero(numeroTuile);
					parcoureur++;
					daoTuileRessource.save(maTuile);
				}

			}

			for (numeroTuile = 8; numeroTuile < 12; numeroTuile++) {
				for (int j = 0; j < 2; j++) {
					maTuile = (TuileRessource) mesTuiles.get(parcoureur);
					maTuile.setNumero(numeroTuile);
					parcoureur++;
					daoTuileRessource.save(maTuile);
				}

			}

			maTuile = (TuileRessource) mesTuiles.get(parcoureur);
			maTuile.setNumero(numeroTuile);
			daoTuileRessource.save(maTuile);
		}

		catch (Exception e) {
			System.out.println("Erreur dans placement numero");
			e.printStackTrace();
		}
	}
}
