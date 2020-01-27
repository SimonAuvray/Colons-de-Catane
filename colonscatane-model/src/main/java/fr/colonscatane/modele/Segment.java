package fr.colonscatane.modele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Segment extends PositionPlateau {
	
	public Segment() {
	}


}
