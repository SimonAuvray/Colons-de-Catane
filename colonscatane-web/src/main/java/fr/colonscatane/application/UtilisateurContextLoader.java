package fr.colonscatane.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.Utilisateur;

@ApplicationScope
@Component
public class UtilisateurContextLoader {
	

	private List<Utilisateur> utilisateursConnectes;
		
	public List<Utilisateur> getUtilisateurs() {
		if(this.utilisateursConnectes == null) {
			this.utilisateursConnectes = new ArrayList<Utilisateur>();
		}
		return utilisateursConnectes;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateursConnectes = utilisateurs;
	}
		
	public void addUtilisateur(Utilisateur utilisateur) {
			
		if(this.utilisateursConnectes == null) {
			this.utilisateursConnectes = new ArrayList<Utilisateur>();
		}
		utilisateursConnectes.add(utilisateur);
	}
	
	public Utilisateur findUtilisateurByIdSession (int idSession) {
		
		Utilisateur monUt = new Joueur();
		
		return monUt;
	}
	
	
	
	

}
