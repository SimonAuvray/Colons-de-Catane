package fr.colonscatane.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypeTuile;

@Service
public class PartieService {
	
	@Autowired
	private IDAOTuileRessource daoTuile;
	
	@Autowired
	private IDAOJoueur daoJoueur;

	public void inscription() {
		// TODO Auto-generated method stub
		
	}
	

	
	public void distributionRessource() {
		
		//génération aléatoire du résultat du dé
		
		int de ;
		de = (int) (Math.random() * (10)+2);
		
		if (de !=7) {
			
			// détection des tuiles selectionnées
			
			
			List<TuileRessource> mesTuiles =  daoTuile.findByNumero(de).get();
			
			
			List<Coin> mesCoinsOccupes = new ArrayList<Coin>();
			
		
			
			// distribution aux joueurs possédant un des coins
			
			for (TuileRessource t : mesTuiles) {
				
				TypeTuile ressource = t.getType();
				
				//récupération des coins associés occupés
				
				
				
				List<Coin> mesCoins = t.getListeCoin();
				for (Coin c : mesCoins) {
					if (c.getOccupation() !=null) {
						mesCoinsOccupes.add(c);
						}
					}
				
				
				
				if (mesCoinsOccupes != null) {
				
					for (Coin c : mesCoinsOccupes) {
						Joueur monJoueur = c.getOccupation();
						
						if (ressource.equals(TypeTuile.Prairie)){
							monJoueur.setMoutonPossede(monJoueur.getMoutonPossede() + c.getTaille());
							
						}
						
						if (ressource.equals(TypeTuile.Champ)){
							monJoueur.setBlePossede(monJoueur.getBlePossede() + c.getTaille());
							
						}
						
						if (ressource.equals(TypeTuile.Carriere)){
							monJoueur.setArgilePossede(monJoueur.getArgilePossede() + c.getTaille());
							
						}
						
						if (ressource.equals(TypeTuile.Montagne)){
							monJoueur.setPierrePossede(monJoueur.getPierrePossede() + c.getTaille());
							
						}
						
						daoJoueur.save(monJoueur);
					}
				}
				
			}
			
			
			
			
			
			
			
			
		}
	}
	
	
	
	
	
	
}
