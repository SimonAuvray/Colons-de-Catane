package fr.colonscatane.modele;

public abstract class  Utilisateur {
	protected String nom;
	
	protected String username;
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
