package fr.colonscatane.dao;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TypePosition;

@Primary
public interface IDAOPositionPlateau<T extends PositionPlateau> extends JpaRepository<T, Integer>{
	public List<PositionPlateau> findByType(TypePosition type);
	
	public PositionPlateau findByXAndY(int x, int y);
	
	@Query(value = "TRUNCATE TABLE LIENS", nativeQuery = true)
	@Modifying
	public void dropLiens();
	
	@Query(value = "TRUNCATE TABLE POSITION_PLATEAU", nativeQuery = true)
	@Modifying
	public void resetIncrement();
}
