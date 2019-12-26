package fr.colonscatane.modele;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Joueur extends Utilisateur {
	
	
	
	private Couleur couleur;
	private int score;
	private int boisPossede;
	private int blePossede;
	private int argilePossede;


	private int pierrePossede;
	private int moutonPossede;
	


	private int compteurColonie;
	private int compteurVille;
	private int compteurRoute;
	
	@ManyToOne
	@JoinColumn(name = "JOUEUR_PARTIE")
	private Partie partie;
	
	@OneToMany(mappedBy = "occupationCoin")
	private List<Coin> coins;
	
	@OneToMany(mappedBy = "occupationSegment")
	private List<Segment> segments;
	
	public Couleur getCouleur() {
		return couleur;
	}
		
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
	public String getNomCouleur () {
		return this.couleur.toString();
	}
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBoisPossede() {
		return boisPossede;
	}
	public void setBoisPossede(int boisPossede) {
		this.boisPossede = boisPossede;
	}
	public int getBlePossede() {
		return blePossede;
	}
	public void setBlePossede(int blePossede) {
		this.blePossede = blePossede;}
		
	public int getArgilePossede() {
		return argilePossede;
	}

	public void setArgilePossede(int argilePossede) {
		this.argilePossede = argilePossede;
	}
	
	public int getPierrePossede() {
		return pierrePossede;
	}
	public void setPierrePossede(int pierrePossede) {
		this.pierrePossede = pierrePossede;
	}
	public int getMoutonPossede() {
		return moutonPossede;
	}
	public void setMoutonPossede(int moutonPossede) {
		this.moutonPossede = moutonPossede;
	}
	public int getCompteurColonie() {
		return compteurColonie;
	}
	public void setCompteurColonie(int compteurColonie) {
		this.compteurColonie = compteurColonie;
	}
	public int getCompteurVille() {
		return compteurVille;
	}
	public void setCompteurVille(int compteurVille) {
		this.compteurVille = compteurVille;
	}
	public int getCompteurRoute() {
		return compteurRoute;
	}
	public void setCompteurRoute(int compteurRoute) {
		this.compteurRoute = compteurRoute;
	}
	
	
	public Joueur () {
	}
	
	public Joueur (String nom) {
		this.nom = nom;
	}
	
	public Joueur (Couleur couleur, String nom) {
		super(nom);
		this.couleur = couleur;
	}
	
	
	public Joueur (Couleur couleur, String nom, int score,int boisPossede, int blePossede, int argilePossede, int pierrePossede, int moutonPossede, int compteurColonie, int compteurVille, int compteurRoute) {
		this(couleur, nom) ;
		this.score = score;
		this.boisPossede = boisPossede;
		this.blePossede = blePossede ;
		this.argilePossede = argilePossede;
		this.pierrePossede = pierrePossede;
		this.moutonPossede = moutonPossede;
		this.compteurColonie = compteurColonie;
		this.compteurVille = compteurVille;
		this.compteurRoute = compteurRoute;
	}
	
	
	@Override
	public String toString() {
		return "Joueur [couleur=" + couleur + ", nom=" + nom + "]";
	}
	
	
	
	
		
}
