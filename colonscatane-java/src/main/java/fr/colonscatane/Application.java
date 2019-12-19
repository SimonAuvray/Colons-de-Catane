package fr.colonscatane;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



import fr.colonscatane.dao.ConnectionSQL;
import fr.colonscatane.dao.*;
import fr.colonscatane.modele.*;

public class Application {

	

		
		public static Scanner sc = new Scanner(System.in);
		public static DAOJoueur daoJoueur = new DAOJoueur();
		public static DAOPositionPlateau daoPositionPlateau = new DAOPositionPlateau();
		public static Partie partieEnCours = new Partie();

		
		public static void main(String[] args) {
			
			
			
			
			daoJoueur.deleteAll();
			daoPositionPlateau.deleteAll();
			inscription();
			initialisation();
			
			
			PositionPlateau maPos = daoPositionPlateau.findByXY(1, 1);
			
			System.out.println(maPos.getId());
			
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
		    
	    	//premiere moitiee de tableau​
	    	//pour chaque ligne avec alternance coin/segment
	    	
	    	int nonAchetable = 8; 
	    	//pour chaque ligne avec alternance coin/segment
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
	        			}
	    			}
	    		}
	    		nonAchetable -= 2;
	    	}
	    	
	    	nonAchetable = 4 ;
	    	//pour la "descente" du plateau
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
	    		}
	    	}
	    	
	    	System.out.println("plateau vide , mais pret");
	    }
}
