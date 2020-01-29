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
import fr.colonscatane.modele.ROLE;
import fr.colonscatane.modele.Partie;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypePosition;
import fr.colonscatane.modele.TypeTuile;
import fr.colonscatane.modele.Utilisateur;
import fr.colonscatane.service.CoinService;
import fr.colonscatane.service.PositionPlateauService;
import fr.colonscatane.service.SegmentService;
import fr.colonscatane.service.TuileRessourceService;

@Configuration
@ComponentScan("fr.colonscatane")
public class Application {

	public static Scanner sc = new Scanner(System.in);

	@Autowired
	private IDAOUtilisateur<Utilisateur> daoUtilisateur;

	@Autowired
	private IDAOJoueur daoJoueur;

	@Autowired
	private IDAOPositionPlateau<PositionPlateau> daoPositionPlateau;

	@Autowired
	private CoinService servCoin;

	@Autowired
	private PositionPlateauService servPositionPlateau;

	@Autowired
	private SegmentService servSegment;

	@Autowired
	private TuileRessourceService servTuileRessource;

	public static Partie partieEnCours = new Partie();;

	public void run(String[] args) {

		//inscription();
		servPositionPlateau.initialisationPlateau();
		servCoin.addRessources();
		servTuileRessource.placementRessource();
		servTuileRessource.placementNumero();

		partieEnCours.ordreSetUp();
		for (Joueur j : partieEnCours.getLstJoueurs()) {
			daoJoueur.save(j);
		}

		premiersTours();

		// deleteJeu();

	}

	public static void main(String[] args) {

		AnnotationConfigApplicationContext myContext = new AnnotationConfigApplicationContext(Application.class);
		myContext.getBean("application", Application.class).run(args);

		myContext.close();
	}

	/**
	 * Cr�ation de la liste des joueur pour la partie en cours
	 */
	public void inscription() {
		boolean saisieOK = false;
		int nombreDeJoueurs = 0;
		String username = null;
		String password = null;

		// inscription �ventuelle d'un nouvel utilisateur

		inscriptionUt();

		// definition du nombre de joueurs
		while (!saisieOK) {

			System.out.println("Combien de joueur ?");
			try {
				nombreDeJoueurs = sc.nextInt();
				saisieOK = true;
			} catch (InputMismatchException e) {
				System.out.println("ERR : Le nombre de joueur doit etre un entier");
				sc.nextLine();
			}
			if (saisieOK) {
				if (nombreDeJoueurs > Partie.NB_JOUEUR_MAX || nombreDeJoueurs < Partie.NB_JOUEUR_MIN) {
					saisieOK = false;
					System.out.println(
							"ERR : Le nombre de joueur pour la partie ne peut pas �tre sup�rieur � 4 ou inf�rieur � 2");
				}
				sc.nextLine();
			}
		}

		// definition des joueurs

		int i = 1;
		while (i <= nombreDeJoueurs) {
			boolean identification = false;

			while (!identification) {
				System.out.println("Quel est le nom d'utilisateur du joueur " + i + "?");
				username = sc.nextLine();

				System.out.println("Entrer le mot de passe de l'utilisateur :");
				password = sc.nextLine();

				try {
					daoUtilisateur.findByUsernameAndPassword(username, password).orElse(null).getId();
					identification = true;
					System.out.println("Identifiction ok ! Bonjour " + username);
				}

				catch (Exception ne) {
					System.out.println(
							"Ce mot de passe ne correspond pas � ce nom d'utilisateur, veuillez r�essayer");
				}

			}
			Joueur joueur = (Joueur) daoUtilisateur.findByUsernameAndPassword(username, password).orElse(null);
			joueur.setRole(ROLE.Joueur);
			if (partieEnCours.getLstJoueurs().stream().filter(j -> j.getUsername().equals(joueur.getUsername()))
					.count() > 0) {
				System.out.println("Les joueurs doivent tous �tre des utilisateurs diff�rents");
				System.out.println("Saisissez un autre nom pour le joueur " + i);
			} else {
				partieEnCours.getLstJoueurs().add(joueur);
				i++;
			}

		}

		// attribution d'une couleur � un joueur
		partieEnCours.attribuerCouleur();
		for (Joueur j : partieEnCours.getLstJoueurs()) {

			daoJoueur.save(j);

		}

		System.out.println("FIN!!!!!!!!");
	}

	/**
	 * Inscription d'un nouvel utilisateur avec un identifiant et un mot passe
	 * L'utilisateur peut participer � plusieurs partie
	 */
	public void inscriptionUt() {

		boolean saisieInscr = false;
		String instruction = null;
		boolean inscrfinie = false;
		System.out.println("Souhaitez-vous inscrire un nouvel utilisateur ? (y/n)");

		while (!saisieInscr || !inscrfinie) {
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

					else {
						monJoueur.setUsername(username);
					}

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

					else {
						monJoueur.setPassword(password);
					}

				}

				catch (EmptyLibelleException e) {
				}

				catch (Exception e) {
				}

				daoJoueur.save(monJoueur);
				System.out.println("joueur bien cr��");
				// plusieurs inscriptions � la suite ou non

				System.out.println("Souhaitez-vous inscrire un nouveau joueur ? (y/n)");
			}

			if (instruction.equals("n")) {
				inscrfinie = true;

			}
		}
	}

	private void premiersTours() {

		// pour chaque joueur, choisir un coin ou placer la premiere colonie
		for (int i = 0; i <= partieEnCours.getLstJoueurs().size() - 1; i++) {
			Joueur joueurTour = partieEnCours.getLstJoueurs().get(i);
			System.out.println(" Au joueur " + joueurTour.getCouleur() + " de jouer. ");
			System.out
					.println("Joueur " + joueurTour.getCouleur() + " : Choisissez le lieu de votre premiere colonie :");

			int xColonie = 0;
			int yColonie = 0;
			boolean choixOK = false;

			while (!choixOK) {
				try {
					System.out.println(" Ligne : ");
					xColonie = sc.nextInt();
					sc.nextLine();
					System.out.println(" Colonne : ");
					yColonie = sc.nextInt();
					choixOK = true;
				} catch (InputMismatchException e) {
					System.out.println("ERR : le numero de Ligne ou de colonne doit etre un entier.");
					sc.nextLine();
					choixOK = false;
				}
				if (choixOK && servCoin.placerPremiereColonie(xColonie, yColonie, joueurTour)) {
					choixOK = true;
				} else {
					System.out.println("ERR : mauvaise saisie");
					choixOK = false;
				}
			}

			// Disposition d'une route voisine
			System.out.println("Joueur " + joueurTour.getCouleur() + " : Choisissez une route :");
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
					System.out.println("ERR : le num�ro de Ligne ou de colonne doit etre un entier.");
					sc.nextLine();
				}

				saisieRouteOK = servSegment.placerUneRoute(xRoute, yRoute, joueurTour);

			}
		}
	}

	private void deleteJeu() {

		daoPositionPlateau.deleteAll();
		daoPositionPlateau.dropLiens();
		daoPositionPlateau.resetIncrement();
		daoJoueur.deleteAll();
	}
}
