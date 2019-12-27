/**
 * 
 */
package fr.colonscatane.dao;

import java.util.List;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TypePosition;

/**
 * @author aballenghien
 *
 */
public interface IDAOPositionPlateau extends IDAO<PositionPlateau, Integer> {
	public PositionPlateau findByXY(int x, int y);
	
	public List<PositionPlateau> findByType(TypePosition type);
	
	
	public void deleteAll();
}



