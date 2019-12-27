/**
 * 
 */
package fr.colonscatane.dao;

import java.util.List;

import fr.colonscatane.modele.PositionPlateau;

/**
 * @author aballenghien
 *
 */
public interface IDAOPositionPlateau extends IDAO<PositionPlateau, Integer> {
	public PositionPlateau findByXY(int x, int y);
	
	public List<PositionPlateau> findByType(int type);
	
	
	public void deleteAll();
}



