package fr.colonscatane.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import fr.colonscatane.views.Views;

@Entity
@Table(name = "Partie")
public class Partie {
	
		public static final int NB_JOUEUR_MAX = 4;
		public static final int NB_JOUEUR_MIN = 2;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "PARTIE_ID")
		@JsonView(Views.Common.class)
		private int id;

		@OneToMany(mappedBy = "partie")
		@JsonView(Views.PartieWithJoueurs.class)
		List<Joueur> lstJoueurs;
		
		@OneToMany(mappedBy = "partieTour")
		private List<Tour> tours;
		
		public Partie() {
			this.lstJoueurs = new ArrayList<Joueur>();
		}
	
		/**
		 * Attribution d'une couleur à chaque joueur
		 */
		public void attribuerCouleur() {
			for (int i = 0; i < this.lstJoueurs.size(); i++) {
				switch (i) {
				case 0:
					this.lstJoueurs.get(i).setCouleur(Couleur.ROUGE);
					break;
				case 1:
					this.lstJoueurs.get(i).setCouleur(Couleur.BLEU);
					break;
				case 2:
					this.lstJoueurs.get(i).setCouleur(Couleur.NOIR);
					break;
				case 3:
					this.lstJoueurs.get(i).setCouleur(Couleur.VERT);
					break;
				}
			}
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public List<Tour> getTours() {
			return tours;
		}

		public void setTours(List<Tour> tours) {
			this.tours = tours;
		}

		public void setLstJoueurs(List<Joueur> lstJoueurs) {
			this.lstJoueurs = lstJoueurs;
		}

		public List<Joueur> getLstJoueurs(){
			return this.lstJoueurs;
		}
		
		
		/**
		 * L'ordre de passage est tiré au sort
		 */
		public void ordreSetUp() {
			
			System.out.println(" tirage de l'ordre de passage ");
			
			Collections.shuffle(this.getLstJoueurs());
			int i = 0;
			
			for (Joueur j : this.getLstJoueurs()) {
				j.setOrdre(i);
				i++;
			}
			
		}

		
	}

