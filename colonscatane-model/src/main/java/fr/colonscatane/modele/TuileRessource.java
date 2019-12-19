/**
 * 
 */
package fr.colonscatane.modele;
import java.util.ArrayList;
import java.util.List;


public class TuileRessource {
	List<PositionPlateau> lstPositionsPlateau;
	int x;
	int y;
	
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