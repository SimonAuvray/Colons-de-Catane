package fr.colonscatane.modele;

import java.util.*;

public class Partie {
	
		public static final int NB_JOUEUR_MAX = 4;
		public static final int NB_JOUEUR_MIN = 2;
		
		List<Joueur> lstJoueurs;
		public Partie() {
			this.lstJoueurs = new ArrayList<Joueur>();
		}
	
		public void attribuerCouleur() {
			for (int i = 0; i < this.lstJoueurs.size(); i++) {
				switch (i) {
				case 0:
					this.lstJoueurs.get(i).setCouleur(Couleur.BLEU);
					break;
				case 1:
					this.lstJoueurs.get(i).setCouleur(Couleur.VERT);
					break;
				case 2:
					this.lstJoueurs.get(i).setCouleur(Couleur.ROUGE);
					break;
				case 3:
					this.lstJoueurs.get(i).setCouleur(Couleur.NOIR);
					break;
				}
			}
		}
		
		public List<Joueur> getLstJoueurs(){
			return this.lstJoueurs;
		}
		
	}

