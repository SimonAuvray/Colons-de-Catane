package fr.colonscatane;

import java.util.InputMismatchException;
import java.util.Scanner;

import fr.colonscatane.dao.DAOJoueur;
import fr.colonscatane.dao.DAOPositionPlateau;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.hibernate.DAOJoueurHibernate;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.modele.TuileRessource;

public class Application {
	
		public static Scanner sc = new Scanner(System.in);
		public static IDAOJoueur daoJoueur = new DAOJoueurHibernate();
		public static DAOPositionPlateau daoPositionPlateau = new DAOPositionPlateau();
		public static Partie partieEnCours = new Partie();
		
		public static void main(String[] args) {
	
			daoJoueur.deleteAll();
			daoPositionPlateau.deleteAll();
			inscription();
			initialisation();
			
			
//			PositionPlateau maPos = daoPositionPlateau.findByXY(1, 1);
//			
//			System.out.println(maPos.getId());
			
			sc.close();

		
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
	    	
			int compteurTuile = -1;
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
	    	nonAchetable = 8 ;
	    	for (int i = 3 ; i < 8 ; i +=2) {
	    		if ( i == 5) {
	    			for (int j = nonAchetable ; j < 29-nonAchetable ; j++) {
	    				int starter = j+2 ;
	        			if ( starter % 4 == 0 ) {
	            			PositionPlateau positionPlateau = new Segment();
	        				positionPlateau.setX(i);
	        				positionPlateau.setY(j);
	        				daoPositionPlateau.save(positionPlateau);
	            		}
	        			else {
	        				//c'est une tuile
	        				if(compteurTuile % 2 == 0) {
	        					PositionPlateau positionPlateau = new TuileRessource();
	        					positionPlateau.setX(i);
	        					positionPlateau.setY(j);
	        					daoPositionPlateau.save(positionPlateau);
	        				}
	        				compteurTuile += 1 ;	        				
	        			}
	    			}
	    		}
	    		else {
	    			for (int j = nonAchetable ; j < 29-nonAchetable ; j++) {
	        			if ( j % 4 == 0 ) {
	            			PositionPlateau positionPlateau = new Segment();
	        				positionPlateau.setX(i);
	        				positionPlateau.setY(j);
	        				daoPositionPlateau.save(positionPlateau);
	            		}
	        			else {
	        				//c'est une tuile
	        				if(compteurTuile % 2 == 0) {
	        					PositionPlateau positionPlateau = new TuileRessource();
	        					positionPlateau.setX(i);
	        					positionPlateau.setY(j);
	        					daoPositionPlateau.save(positionPlateau);
	        				}
	        				compteurTuile += 1 ;	
	        			}
	    			}
	    		}
	    		nonAchetable -= 2;
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
	    			if(compteurTuile % 2 == 0) {
    					PositionPlateau positionPlateau = new TuileRessource();
    					positionPlateau.setX(9);
    					positionPlateau.setY(j);
    					daoPositionPlateau.save(positionPlateau);
    				}
    				compteurTuile += 1 ;	
	    			}
	    		}
	    	
	    	//ligne de tuile 11
	    	for (int j = 8 ; j < 21 ; j++) {
	    		if ( j % 4 == 0 ) {
	    			PositionPlateau positionPlateau = new Segment();
	    			positionPlateau.setX(11);
	   				positionPlateau.setY(j);
	   				daoPositionPlateau.save(positionPlateau);
	   			}
	    		else {
	    				//c'est une tuile
	    			if(compteurTuile % 2 == 0) {
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
}
