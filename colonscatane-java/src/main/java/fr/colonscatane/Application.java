package fr.colonscatane;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.colonscatane.dao.IDAOCoin;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOPositionPlateau;
import fr.colonscatane.dao.IDAOSegment;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.dao.IDAOUtilisateur;
import fr.colonscatane.exception.EmptyLibelleException;
import fr.colonscatane.exception.NotBooleanException;

import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ListeRoles;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypePosition;
import fr.colonscatane.modele.TypeTuile;
import fr.colonscatane.modele.Utilisateur;
import fr.colonscatane.service.CoinService;

@Configuration
@ComponentScan("fr.colonscatane")
public class Application {
	
	public static Scanner sc = new Scanner(System.in);
	
	@Autowired
	public IDAOJoueur daoJoueur;
	@Autowired
	public IDAOUtilisateur daoUtilisateur;
	@Autowired
	public IDAOPositionPlateau daoPositionPlateau;
	@Autowired
	public IDAOCoin daoCoin;
	@Autowired
	public IDAOSegment daoSegment;
	@Autowired
	public IDAOTuileRessource daoTuileRessource;
	@Autowired
	public CoinService srvCoin;
	
	
	public static Partie partieEnCours = new Partie();
		
	public void run(String[] args) {
				
//		deleteJeu();
//		connexionUtilisateur();
		
		
		
//		initialisation();
//		inscription();
//		
//		liaisonTuileCoin();
//		
////		placementRessource();
////		placementNumero();
//		
//		partieEnCours.ordreSetUp();
//		for (Joueur j : partieEnCours.getLstJoueurs()) {
//			daoJoueur.save(j);
//		}
		
		dropLesLiens();
		
		
//		premiersTours();
		
//		dropLesLiens();
		
	}

		public static void main(String[] args) {
			
			AnnotationConfigApplicationContext myContext = new AnnotationConfigApplicationContext(Application.class);
			myContext.getBean("application", Application.class).run(args);
			
			
			
			
			myContext.close();
		}
		
//		private void connexionUtilisateur() {
//			boolean utilisateur;
//			String answer = null;
//			System.out.println(" Etes vous dï¿½jï¿½ un utilisateur ? :Y/N");
//			while(!answer.contentEquals("Y") || !answer.contentEquals("N")) {
//				try {
//					answer = sc.nextLine();
//				} catch (InputMismatchException e) {
//					System.out.println("Veuillez entrer Y ou N");
//					sc.nextLine();
//				}
//			}
//			if(answer.contentEquals("Y")) {
//				utilisateur = true;
//				Boolean saisieOK = false;
//				String nomUtilisateur = null;
//				String passwordUtilisateur = null;
//				while(!saisieOK) {
//					try {
//						System.out.println(" Votre nom d'utlisateur :");
//						nomUtilisateur = sc.next();
//						System.out.println(" Votre mot de passe :");
//						passwordUtilisateur = sc.next();
//					} catch (InputMismatchException e) {
//						System.out.println("erreur d'entrï¿½e");
//						sc.nextLine();
//					}
//				}
//				daoUtilisateur.findByUsernameAndPassword(nomUtilisateur, passwordUtilisateur);
//			}
//		}

		/**
		 * CrÃ©ation de la liste des joueur pour la partie en cours
		 */
		public void inscription() {
			boolean saisieOK = false;
			int nombreDeJoueurs = 0;
			String username = null;
			String password = null;
			
			
			
			// inscription éventuelle d'un nouvel utilisateur
			
			inscriptionUt();
			
		
			// definition du nombre de joueurs
			while (!saisieOK) {
				
				System.out.println("Combien de joueur ?");
				try {
					nombreDeJoueurs = sc.nextInt();
					saisieOK = true;
				} catch (InputMismatchException e) {
					System.out.println("ERR : Le nombre de joueur doit Ãªtre un entier");
					sc.nextLine();
				}
				if (saisieOK) {
					if (nombreDeJoueurs > Partie.NB_JOUEUR_MAX || nombreDeJoueurs < Partie.NB_JOUEUR_MIN) {
						saisieOK = false;
						System.out.println("ERR : Le nombre de joueur pour la partie ne peut pas Ãªtre supÃ©rieur Ã  4 ou infÃ©rieur Ã  2");
					}
					sc.nextLine();
				}
			}
			
			// definition des joueurs
			
		
			int i = 1;
			while(i <= nombreDeJoueurs) {
				boolean identification = false ;
				
				int idUt = 0;
				
			
				while (!identification) {
					System.out.println("Quel est le nom d'utilisateur du joueur "+i+"?");
					username = sc.nextLine();
					
					System.out.println("Entrer le mot de passe de l'utilisateur :");
					password = sc.nextLine();
					
					try { 
						idUt = daoUtilisateur.findByUsernameAndPassword(username, password).getId();
						identification = true;
						System.out.println("Identifiction ok ! Bonjour " + username);
					}
					
					catch (Exception ne) {
						System.out.println("Ce mot de passe ne correspond pas à ce nom d'utilisateur, veuillez réessayer");
					}
				}
				
				Joueur joueur = (Joueur) daoUtilisateur.findByUsernameAndPassword(username, password);
				joueur.setRole(ListeRoles.Joueur);
				
				partieEnCours.getLstJoueurs().add(joueur);
				
				i++;
			}
			
			// attribution d'une couleur à un joueur
			partieEnCours.attribuerCouleur();
			for(Joueur j : partieEnCours.getLstJoueurs()) {
				
				daoJoueur.save(j);
				
			}
		
			System.out.println("FIN!!!!!!!!");
		
//			int i2 = 1;
//			while(i2 <= nombreDeJoueurs) {
//				System.out.println("Quel est le nom du joueur "+i+"?");
//				String nom = Application.sc.nextLine();
//				if(nom.equals("")) {
//					nom = "Toto";
//				}
//				
//				Joueur joueur = new Joueur(nom);
//				
//				partieEnCours.getLstJoueurs().add(joueur);
//				i2++;
//			}
//			partieEnCours.attribuerCouleur();
//			for(Joueur j : partieEnCours.getLstJoueurs()) {
//				daoJoueur.save(j);
//				System.out.println(j.toString());
//			}
		}

public void inscriptionUt() {
	
	boolean saisieInscr = false;
	String instruction = null ;
	boolean inscrfinie = false;
	System.out.println("Souhaitez-vous inscrire un nouveau joueur ? (y/n)");
	
	while(!saisieInscr || !inscrfinie) {
		try {
			instruction = sc.nextLine();
			
			if (!instruction.equals("y") && !instruction.equals("n")) {
				throw new NotBooleanException();
			}
			saisieInscr = true;
			
			
		} 
		
		catch (InputMismatchException e) {
			System.out.println("ERR : taper 'y' pour oui, 'n' pour non");
			e.printStackTrace();
			sc.nextLine();
		}
		
		catch (NotBooleanException e) {
			System.out.println("ERR : taper 'y' pour oui, 'n' pour non");
			sc.nextLine();
		}
		

	
	if (instruction.equals("y")) {
		Joueur monJoueur = new Joueur();
		System.out.println("Entrer un nom d'utilisateur :");
		
		
		// definition du nom d'utilisateur
		try {
			String username = null;
			
			username = sc.nextLine();
			System.out.println(username);
			if (username == null) {
				throw new EmptyLibelleException();
			}
			
			else { monJoueur.setUsername(username);}
			
		}
		
		catch (EmptyLibelleException e) {
			System.out.println("");
		}
		
		catch (Exception e) {
			System.out.println("");
		}
		
		// definition du mot de passe
		
		try {
			String password = null;
			
			System.out.println("Entrer un mot de passe :");
			password = sc.nextLine();
			if (password == null) {
				throw new EmptyLibelleException();
			}
			
			else { monJoueur.setPassword(password);}
			
		}
		
		catch (EmptyLibelleException e) {
		}
		
		catch (Exception e) {
		}
		
		daoJoueur.save(monJoueur);
		System.out.println("joueur bien créé");
		
		// plusieurs inscriptions à la suite ou non
		System.out.println("Souhaitez-vous inscrire un nouveau joueur ? (y/n)");
	}
			
		if (instruction.equals("n")) {
			inscrfinie = true;
			
		}
	}
}
	
		

		
		
		
		public void initialisation() {
		    
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
		
		
		
		
		public void placementRessource () {
			
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
		
		
		
		public void placementNumero () {
			
			List<PositionPlateau> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);
			
			Collections.shuffle(mesTuiles);
			
			List<TuileRessource> ListDesert = daoTuileRessource.findByTypeRessource(TypeTuile.Desert);
			
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
		
		
	private void liaisonTuileCoin() {
		// TODO Auto-generated method stub
		List<PositionPlateau> mesTuiles = daoTuileRessource.findByType(TypePosition.TuileRessource);
		srvCoin.findRessources();
		for (PositionPlateau T : mesTuiles) {
			int x = T.getX();
			int y = T.getY();
			try {
//				Coin coin = (Coin)daoCoin.findByXAndY( x-1 , y-2 );
//				coin.getRessources().add((TuileRessource)T);
//				Coin coin2 = (Coin)daoCoin.findByXAndY(x-1, y);
//				coin2.getRessources().add((TuileRessource)T);
//				Coin coin3 = (Coin)daoCoin.findByXAndY(x-1, y+2);
//				coin3.getRessources().add((TuileRessource)T);
//				
//				Coin coin4 = (Coin)daoCoin.findByXAndY(x+1, y-2);
//				coin4.getRessources().add((TuileRessource)T);
//				Coin coin5 = (Coin)daoCoin.findByXAndY(x+1, y);
//				coin5.getRessources().add((TuileRessource)T);
//				Coin coin6 = (Coin)daoCoin.findByXAndY(x+1, y+2);
//				coin6.getRessources().add((TuileRessource)T);
//				
//				daoCoin.save(coin);
//				daoCoin.save(coin2);
//				daoCoin.save(coin3);
//				daoCoin.save(coin4);
//				daoCoin.save(coin5);
//				daoCoin.save(coin6);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("erreur de configuration du plateau; liaisonTuileCoin");
			}
		}
	}
	


	private void premiersTours() {
		
		//pour chaque joueur, choisir un coin ou placer la premiere colonie
		for (int i = 0 ; i <= partieEnCours.getLstJoueurs().size() - 1 ; i ++) {
			Joueur joueurTour = partieEnCours.getLstJoueurs().get(i);
			System.out.println(" Au joueur "+ joueurTour.getCouleur() + " de jouer. ");
			System.out.println(" Choisissez le lieu de votre premiï¿½re colonie :");
			
			int xColonie = 0;
			int yColonie = 0;
			boolean saisieOK = false;
			Coin coin = new Coin();
			
			while (!saisieOK) {
				try {
					System.out.println(" Ligne : ");
					xColonie = sc.nextInt();
					sc.nextLine();
					System.out.println(" Colonne : ");
					yColonie = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("ERR : le numï¿½ro de Ligne ou de colonne doit etre un entier.");
					sc.nextLine();
				}
				
				try {
					coin = (Coin)daoCoin.findByXAndY(xColonie, yColonie);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Le lieu choisi n'est pas un coin et ne peut recevoir une colonnie !");
				}
				
				//test si coin deja occupï¿½
					//ajouter tests si coins voisins !!
					//faire des Pos plateau essayer getOcc?
				Coin coin1 = new Coin();
				Coin coin2 = new Coin();
				Coin coin3 = new Coin();
				Coin coin4 = new Coin();
				try {
					coin1 = (Coin) daoCoin.findByXAndY(xColonie, yColonie-2);
				}
				finally {
				}
				try {
					coin2 = (Coin) daoCoin.findByXAndY(xColonie, yColonie+2);
				}
				finally {
				}
				try {
					coin3 = (Coin) daoCoin.findByXAndY(xColonie-2, yColonie);
				}
				finally {
				}
				try {
					coin4 = (Coin) daoCoin.findByXAndY(xColonie+2, yColonie);
				}
				finally {
				}

				if (coin.getOccupationCoin() == null ) {
					coin.setOccupationCoin(joueurTour);
					daoCoin.save(coin);
					saisieOK = true;
				} else {
					System.out.println("Le lieu choisi est deja occupï¿½ !");
				}
			}
			
			//Disposition d'une route voisine
			System.out.println(" Choisissez une route :");
			Boolean saisieRouteOK = false;
			int xRoute = 0;
			int yRoute = 0;
			Segment route = new Segment();
			
			while (!saisieRouteOK) {
				try {
					System.out.println(" Ligne : ");
					xRoute = sc.nextInt();
					sc.nextLine();
					System.out.println(" Colonne : ");
					yRoute = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("ERR : le numï¿½ro de Ligne ou de colonne doit etre un entier.");
					sc.nextLine();
				}
				
				try {
					route = (Segment)daoSegment.findByXAndY(xRoute, yRoute);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Vous n'avez pas saisi une route !");
				}
				
				if ( (xRoute < xColonie -1) || (xRoute > xColonie +1) || (yRoute < yColonie -1) || (yRoute > yColonie +1) ) {
					System.out.println("Vous devez choisir une route voisine de votre colonie !");
				}
				else {
					route.setOccupationSegment(joueurTour);
					daoSegment.save(route);
					saisieRouteOK = true;
				}
			}
		}
	}


	private void deleteJeu() {

		daoPositionPlateau.deleteAll();
		daoPositionPlateau.resetIncrement();
		daoJoueur.deleteAll();
	}

	private void dropLesLiens() {

		daoPositionPlateau.dropLiens();
		
	}
}
