package fr.colonscatane.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TypePosition;

@Primary
public interface IDAOPositionPlateau<T extends PositionPlateau> extends JpaRepository<T, Integer>{
	public List<T> findByType(TypePosition type);
	
	public Optional<T> findByXAndY(int x, int y);
	
	@Query(value = "DROP TABLE LIENS", nativeQuery = true)
	@Modifying
	@Transactional
	public void dropLiens();
	
	@Query(value = "ALTER TABLE position_plateau AUTO_INCREMENT=1", nativeQuery = true)
	@Modifying
	@Transactional
	public void resetIncrement();
}
