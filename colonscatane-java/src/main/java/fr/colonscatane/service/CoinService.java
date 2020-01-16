package fr.colonscatane.service;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.colonscatane.dao.IDAOCoin;
import fr.colonscatane.dao.IDAOPositionPlateau;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TuileRessource;

@Service
public class CoinService {
		
	@Autowired
	private IDAOTuileRessource daoTuileRessource;
	@Autowired
	private IDAOCoin daoCoin;
	
	@Transactional
	public void findRessources() {
		try {
			daoTuileRessource.findAll().forEach(new Consumer<TuileRessource>() {
				public void accept(TuileRessource t) {
					t.getListeCoin().add((Coin) daoCoin.findByXAndY(t.getX() -1, t.getY()-1));
					daoTuileRessource.save(t);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERR: impossible de trouver les tuiles associées au coin");
		}
	}

}
