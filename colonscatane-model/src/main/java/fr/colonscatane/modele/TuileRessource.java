/**
 * 
 */
package fr.colonscatane.modele;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("3")
public class TuileRessource extends PositionPlateau {
	
	@ManyToMany(mappedBy = "ressources")
	List<Coin> listeCoin;
	
	public TuileRessource() {
		this.listeCoin = new ArrayList<Coin>();
	}


	public TuileRessource(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public List<Coin> getLstPositionsPlateau() {
		return listeCoin;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	

	
}