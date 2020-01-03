package fr.colonscatane.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateur")
public abstract class  Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UT_ID")
	protected int id;
	
	@Column(name = "UT_NOM")
	protected String nom;
	
	@Column(name = "UT_USERNAME", unique = true)
	protected String username;
	
	@Column(name = "UT_PASSWORD")
	protected String password;	
	
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
	
	
	
}
