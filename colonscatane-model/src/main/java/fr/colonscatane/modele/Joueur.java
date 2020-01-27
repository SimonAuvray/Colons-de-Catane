package fr.colonscatane.modele;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "joueur")
@PrimaryKeyJoinColumn(name="JOUEUR_ID", referencedColumnName = "UT_ID")
public class Joueur extends Utilisateur {
	
	
	
	@Column(name = "J_COULEUR")
	private Couleur couleur;
	@Column(name = "J_ORDRE")
	private int ordre;
	@Column(name = "J_SCORE")
	private int score;
	@Column(name = "J_BOIS_POSSEDE")
	private int boisPossede;
	@Column(name = "J_BLE_POSSEDE")
	private int blePossede;
	@Column(name = "J_ARGILE_POSSEDE")
	private int argilePossede;
	@Column(name = "J_PIERRE_POSSEDE")
	private int pierrePossede;
	@Column(name = "J_MOUTON_POSSEDE")
	private int moutonPossede;
	@Column(name = "J_COMPTEUR_COLONIES")
	private int compteurColonie;
	@Column(name = "J_COMPTEUR_VILLE")
	private int compteurVille;
	@Column(name = "J_COMPTEUR_ROUTE")
	private int compteurRoute;
	@Column(name="J_ROLE")
	private ListeRoles role;
	
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
	
	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
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
	public List<Coin> getCoins() {
		return coins;
	}	
	
	
	
	

	public ListeRoles getRole() {
		return role;
	}

	public void setRole(ListeRoles role) {
		this.role = role;
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
	
	public Joueur (String username, String mdp) {
		super(username, mdp);
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
