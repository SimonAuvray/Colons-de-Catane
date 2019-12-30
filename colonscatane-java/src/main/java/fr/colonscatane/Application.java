package fr.colonscatane;

import java.util.ArrayList;
import java.util.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import fr.colonscatane.hibernate.ConnexionHibernate;
import fr.colonscatane.hibernate.DAOCoinHibernate;
import fr.colonscatane.hibernate.DAOJoueurHibernate;
import fr.colonscatane.hibernate.DAOPositionPlateauHibernate;
import fr.colonscatane.hibernate.DAOTuileRessourceHibernate;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypePosition;
import fr.colonscatane.modele.TypeTuile;

public class Application {
	
		public static Scanner sc = new Scanner(System.in);
		public static DAOJoueurHibernate daoJoueur = new DAOJoueurHibernate();
		public static DAOPositionPlateauHibernate daoPositionPlateau = new DAOPositionPlateauHibernate();
		public static DAOTuileRessourceHibernate daoTuileRessource = new DAOTuileRessourceHibernate();
		public static Partie partieEnCours = new Partie();
		
		public static void main(String[] args) {
			
			//deleteJeu();
			
			
			
			//inscription();
			//initialisation();
			
		
			
		//daoPositionPlateau.findByType(TypePosition.TuileRessource);
		placementRessource();
		placementNumero();
			
//			liaisonTuileCoin();
			
//			placementRessource();
//			placementNumero();
			
			
//			PositionPlateau maPos = daoPositionPlateau.findByXY(1, 1);
//			
//			System.out.println(maPos.getId());
			
			
			ConnexionHibernate.close();

		
		}



		/**
		 * Création de la liste des joueur pour la partie en cours
		 */
		public static void inscription() {
			boolean saisieOK = false;
			int nombreDeJoueurs = 0;
			while (!saisieOK) {
				
				System.out.println("Combien de joueur ?");
				try {
					nombreDeJoueurs = sc.nextInt();
					saisieOK = true;
				} catch (InputMismatchException e) {
					System.out.println("ERR : Le nombre de joueur doit être un entier");
					sc.nextLine();
				}
				if (saisieOK) {
					if (nombreDeJoueurs > Partie.NB_JOUEUR_MAX || nombreDeJoueurs < Partie.NB_JOUEUR_MIN) {
						saisieOK = false;
						System.out.println("ERR : Le nombre de joueur pour la partie ne peut pas être supérieur à 4 ou inférieur à 2");
					}
					sc.nextLine();
				}
			}
			
		
			int i = 1;
			while(i <= nombreDeJoueurs) {
				System.out.println("Quel est le nom du joueur "+i+"?");
				String nom = Application.sc.nextLine();
				if(nom.equals("")) {
					nom = "Toto";
				}
				
				Joueur joueur = new Joueur(nom);
				
				partieEnCours.getLstJoueurs().add(joueur);
				i++;
			}
			partieEnCours.attribuerCouleur();
			for(Joueur j : partieEnCours.getLstJoueurs()) {
				daoJoueur.save(j);
				System.out.println(j.toString());
			}
		}
		
		
		
		public static void initialisation() {
		    
	    	//premiere moitiee de tableau
	    	//pour chaque ligne avec alternance coin/segment
				// on ne pose la tuile que au X,Y central (donc une case vide sur 2)
	    	
	    	int nonAchetable = 8; 
	    	for (int i = 2 ; i < 7 ; i +=2) {
	    		for (int j = nonAchetable ; j < 29-nonAchetable ; j++) {
	    			if ( j % 2 == 0 ) {
	    				PositionPlateau positionPlateau = new Coin();
	    				positionPlateau.setX(i);
	    				positionPlateau.setY(j);
	    				daoPositionPlateau.save(positionPlateau);
	    			}
	    			else {
	    				PositionPlateau positionPlateau = new Segment();
	    				positionPlateau.setX(i);
	    				positionPlateau.setY(j);
	    				daoPositionPlateau.save(positionPlateau);
	    			}
	    		}
	    		nonAchetable -= 2 ;
	    	}
	    	//pour chaque "ligne de tuile"
	    	int compteurTuile = -1;
	    	nonAchetable = 8 ;
	    	for (int i = 3 ; i < 8 ; i +=2) {
	    			for (int j = nonAchetable ; j < 29-nonAchetable ; j++) {
	    				int starter;
	    				if( i == 5) {
	    					starter = j + 2;
	    				}
	    				else {
	    					starter = j ;
	    				}
	        			if ( starter % 4 == 0 ) {
	            			PositionPlateau positionPlateau = new Segment();
	        				positionPlateau.setX(i);
	        				positionPlateau.setY(j);
	        				daoPositionPlateau.save(positionPlateau);
	            		}
	        			else {
	        				//c'est une tuile
	        				if(compteurTuile % 3 == 0) {
	        					PositionPlateau positionPlateau = new TuileRessource();
	        					positionPlateau.setX(i);
	        					positionPlateau.setY(j);
	        					daoPositionPlateau.save(positionPlateau);
	        				}
	        				compteurTuile += 1 ;	
	        			}
	    			}
	    			nonAchetable -= 2 ;
	    	}
	    	
	    	//pour la "descente" du plateau
	    	nonAchetable = 4 ;
	    	for (int i = 8 ; i < 13 ; i +=2) {
	    		for (int j = nonAchetable ; j < 29-nonAchetable ; j++) {
	    			if ( j % 2 == 0 ) {
	    				PositionPlateau positionPlateau = new Coin();
	    				positionPlateau.setX(i);
	    				positionPlateau.setY(j);
	    				daoPositionPlateau.save(positionPlateau);
	    			}
	    			else {
	    				PositionPlateau positionPlateau = new Segment();
	    				positionPlateau.setX(i);
	    				positionPlateau.setY(j);
	    				daoPositionPlateau.save(positionPlateau);
	    			}
	    		}
	    		nonAchetable += 2 ;
	    	}
	    	
	    	//ligne de tuile 9
	    	compteurTuile = -1 ;
	    	for (int j = 6 ; j < 23 ; j++) {
	    		int starter = j + 2 ;
	    		if ( starter % 4 == 0 ) {
	    			PositionPlateau positionPlateau = new Segment();
	    			positionPlateau.setX(9);
	   				positionPlateau.setY(j);
	   				daoPositionPlateau.save(positionPlateau);
	   			}
	    		else {
	    				//c'est une tuile
	    			if(compteurTuile % 3 == 0) {
    					PositionPlateau positionPlateau = new TuileRessource();
    					positionPlateau.setX(9);
    					positionPlateau.setY(j);
    					daoPositionPlateau.save(positionPlateau);
    				}
    				compteurTuile += 1 ;	
	    			}
	    		}
	    	
	    	//ligne de tuile 11
	    	compteurTuile = -1 ;
	    	for (int j = 8 ; j < 21 ; j++) {
	    		if ( j % 4 == 0 ) {
	    			PositionPlateau positionPlateau = new Segment();
	    			positionPlateau.setX(11);
	   				positionPlateau.setY(j);
	   				daoPositionPlateau.save(positionPlateau);
	   			}
	    		else {
	    				//c'est une tuile
	    			if(compteurTuile % 3 == 0) {
    					PositionPlateau positionPlateau = new TuileRessource();
    					positionPlateau.setX(11);
    					positionPlateau.setY(j);
    					daoPositionPlateau.save(positionPlateau);
    				}
    				compteurTuile += 1 ;	
	    		}
	    	}
	    	
	    	System.out.println("plateau vide , mais pret");
	    }
		
		
		
		
		public static void placementRessource () {
			
			List<PositionPlateau> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);
			
			Collections.shuffle(mesTuiles);

			
			int compteurPrairie = 4;
			int compteurForet = 4;
			int compteurChamp = 4;
			int compteurMontagne = 3;
			int compteurCarriere = 3;
			
			
			int parcoureur = 0;
			
			try {
			
			for (int i = 0; i<compteurPrairie ; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Prairie);
				parcoureur ++;
				daoTuileRessource.save(maTuile);
			}
			
			
			for (int i = 0; i<compteurForet ; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Foret);
				parcoureur ++;
				daoTuileRessource.save(maTuile);
			}
			
			for (int i = 0; i<compteurChamp ; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Champ);
				parcoureur ++;
				daoTuileRessource.save(maTuile);
			}
			
			for (int i = 0; i<compteurMontagne ; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Montagne);
				parcoureur ++;
				daoTuileRessource.save(maTuile);
			}
			
			for (int i = 0; i<compteurCarriere ; i++) {
				TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
				maTuile.setType(TypeTuile.Carriere);
				parcoureur ++;
				daoTuileRessource.save(maTuile);
			}
			
			TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
			maTuile.setType(TypeTuile.Desert);
			daoTuileRessource.save(maTuile);
			} 
			
			catch (Exception e) {
				System.out.println("Erreur dans placementRessource");
				e.printStackTrace();}
			
			
			
			
		}
		
		
		
		public static void placementNumero () {
			
			List<PositionPlateau> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);
			
			Collections.shuffle(mesTuiles);
			
			List<TuileRessource> ListDesert = daoTuileRessource.findByRessource(TypeTuile.Desert);
			
			TuileRessource desert = ListDesert.get(0);
			
			
			desert.setNumero(0);
			
			int idDesert = mesTuiles.indexOf(desert);
			
			try {
			daoTuileRessource.save(desert);
			mesTuiles.remove(idDesert);
			}
			
			catch (Exception e) {
				System.out.println("erreur desertique");
				e.printStackTrace();
			}
			
			
			int parcoureur = 0;
			int numeroTuile = 2;
			
			try {
			
			TuileRessource maTuile = (TuileRessource) mesTuiles.get(parcoureur);
			maTuile.setNumero(numeroTuile);
			parcoureur ++;
			
			daoTuileRessource.save(maTuile);
			
			for (numeroTuile = 3 ; numeroTuile< 7 ; numeroTuile++) {
				for (int j=0; j<2 ; j++) {
					maTuile = (TuileRessource) mesTuiles.get(parcoureur);
					maTuile.setNumero(numeroTuile);
					parcoureur ++;
					daoTuileRessource.save(maTuile);
				}
				
			}
			
			for (numeroTuile = 8 ; numeroTuile< 12 ; numeroTuile++) {
				for (int j=0; j<2 ; j++) {
					maTuile = (TuileRessource) mesTuiles.get(parcoureur);
					maTuile.setNumero(numeroTuile);
					parcoureur ++;
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
			
			
			
			
			
			
			
			
		

		
		
	private static void liaisonTuileCoin() {
		// TODO Auto-generated method stub
		DAOTuileRessourceHibernate daoTuileRessource = new DAOTuileRessourceHibernate();
		List<PositionPlateau> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);
		for (PositionPlateau T : mesTuiles) {
			int x = T.getX();
			int y = T.getY();
//			System.out.println("tuile " + x + " "+ y);
			try {
//				DAOCoinHibernate daoCoin = new DAOCoinHibernate();
//				Coin coin = (Coin)daoCoin.findByXY(x-1, y-2);
//				System.out.println(coin.getClass());
//				coin.ressources.add((TuileRessource) T);
//				Coin coin2 = (Coin)daoCoin.findByXY(x-1, y);
//				coin2.ressources.add((TuileRessource) T);
//				Coin coin3 = (Coin)daoCoin.findByXY(x-1, y+2);
//				coin3.ressources.add((TuileRessource) T);
//				
//				Coin coin4 = (Coin)daoCoin.findByXY(x+1, y-2);
//				coin4.ressources.add((TuileRessource) T);
//				Coin coin5 = (Coin)daoCoin.findByXY(x+1, y);
//				coin5.ressources.add((TuileRessource) T);
//				Coin coin6 = (Coin)daoCoin.findByXY(x+1, y+2);
//				coin6.ressources.add((TuileRessource) T);
			}
			catch (Exception errPlateau) {
				System.out.println("erreur de configuration du plateau; liaisonTuileCoin");
			}
		}
	}


	private static void deleteJeu() {

		DAOPositionPlateauHibernate daoPositionPlateauHibernate = new DAOPositionPlateauHibernate();
		daoPositionPlateauHibernate.deleteAll();
	}
}
