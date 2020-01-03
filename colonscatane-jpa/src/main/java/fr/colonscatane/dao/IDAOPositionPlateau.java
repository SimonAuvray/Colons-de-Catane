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
	
	public List<PositionPlateau> findByType(TypePosition type);

	PositionPlateau findById(Integer id);

	PositionPlateau save(PositionPlateau entity);

	void delete(PositionPlateau entity);

	void deleteById(Integer id);

	PositionPlateau findByXY(int x, int y);

	void deleteAllPositions();
	
	
}



