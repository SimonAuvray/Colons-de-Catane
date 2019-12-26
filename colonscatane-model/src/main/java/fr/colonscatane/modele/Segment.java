package fr.colonscatane.modele;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Segment extends PositionPlateau {
	
	public Segment() {
	}
	
	@ManyToOne
	@JoinColumn(name = "SEGMENT_JOUEUR")
	protected Joueur occupationSegment;


}
