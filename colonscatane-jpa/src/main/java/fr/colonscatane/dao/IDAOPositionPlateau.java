/**
 * 
 */
package fr.colonscatane.dao;

import fr.colonscatane.modele.PositionPlateau;

/**
 * @author aballenghien
 *
 */
public interface IDAOPositionPlateau extends IDAO<PositionPlateau, Integer> {
	public PositionPlateau findByXY(int x, int y);
	
	public PositionPlateau findByType(PositionPlateau type);
	
	
	public void deleteAll();
}



