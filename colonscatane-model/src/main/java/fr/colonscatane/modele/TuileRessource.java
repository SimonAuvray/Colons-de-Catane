/**
 * 
 */
package fr.colonscatane.modele;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class TuileRessource extends PositionPlateau{
	
	List<PositionPlateau> lstPositionsPlateau;
	
	public TuileRessource() {
		this.lstPositionsPlateau = new ArrayList<PositionPlateau>();
	}


	public TuileRessource(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public List<PositionPlateau> getLstPositionsPlateau() {
		return lstPositionsPlateau;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	

	
}