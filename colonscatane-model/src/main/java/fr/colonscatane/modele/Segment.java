package fr.colonscatane.modele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("2")
public class Segment extends PositionPlateau {
	
	public Segment() {
	}


}
