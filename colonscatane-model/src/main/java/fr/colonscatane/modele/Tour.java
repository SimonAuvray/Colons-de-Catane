package fr.colonscatane.modele;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tour {
	
	@ManyToOne
	@JoinColumn(name = "TOUR_PARTIE")
	private Partie partie;
	
}
