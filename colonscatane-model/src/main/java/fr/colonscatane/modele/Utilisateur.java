package fr.colonscatane.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class  Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UT_ID")
	protected int id;
	
	@Column(name = "UT_NOM")
	protected String nom;
	
	@Column(name = "UT_USERNAME", unique = true, nullable=false)
	@NotBlank(message = "Vous devez entrer votre nom d'utilisateur")
	protected String username;
	
	@Column(name = "UT_PASSWORD", nullable=false)
	@NotBlank(message = "Vous devez entrer votre mot de passe")
	protected String password;	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Utilisateur () {
	}
	
	public Utilisateur (String nom) {
		this.nom = nom;
	}
	
	public Utilisateur (String username, String mdp, String nom) {
		this.username = username;
		this.password=mdp;
		this.nom = nom;
	}
	
	public Utilisateur (String username, String mdp) {
		this.username = username;
		this.password=mdp;
		
	}


	
	
	
	
}
