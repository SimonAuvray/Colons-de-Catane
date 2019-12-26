package fr.colonscatane.modele;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Segment extends PositionPlateau {
	
	public Segment() {
	}
	
	@ManyToOne
	@JoinColumn(name = "SEGMENT_JOUEUR")
	protected Joueur occupationSegment;


}
