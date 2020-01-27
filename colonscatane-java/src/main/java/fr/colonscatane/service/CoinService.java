package fr.colonscatane.service;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.colonscatane.dao.IDAOCoin;
import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.dao.IDAOTuileRessource;
import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.TuileRessource;

@Service
public class CoinService {

	@Autowired
	private IDAOTuileRessource daoTuileRessource;
	@Autowired
	private IDAOCoin daoCoin;
	
	@Autowired
	private IDAOJoueur daoJoueur;

	@Transactional
	public void addRessources() {
		try {
			daoTuileRessource.findAll().forEach(new Consumer<TuileRessource>() {
				public void accept(TuileRessource t) {
					Coin coin = (Coin) daoCoin.findByXAndY(t.getX() - 1, t.getY() - 2).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
					coin = (Coin) daoCoin.findByXAndY(t.getX() - 1, t.getY()).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
					coin = (Coin) daoCoin.findByXAndY(t.getX() - 1, t.getY() + 2).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
					coin = (Coin) daoCoin.findByXAndY(t.getX() + 1, t.getY() - 2).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
					coin = (Coin) daoCoin.findByXAndY(t.getX() + 1, t.getY()).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
					coin = (Coin) daoCoin.findByXAndY(t.getX() + 1, t.getY() + 2).orElse(null);
					coin.getRessources().add(t);
					daoCoin.save(coin);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERR: impossible de trouver les tuiles associ�es au coin");
		}
	}

	/**
	 * Verifie si les coordonnees correspondent a un coin
	 * 
	 * @param xColonie
	 * @param yColonie
	 * @return
	 */
	private boolean isCoin(int xColonie, int yColonie) {
		System.out.println("INF : V�rification de la validit� du coin");
		return ((Coin) daoCoin.findByXAndY(xColonie, yColonie).orElse(null) != null);
	}

	/**
	 * Verifie si le coin est libre de tout joueur
	 * 
	 * @param xColonie
	 * @param yColonie
	 * @return
	 */
	private boolean isLibre(int xColonie, int yColonie) {
		System.out.println("INF : Verification de la libre occupation du coin");
		Coin coin = (Coin) daoCoin.findByXAndY(xColonie, yColonie).orElse(null);
		return (coin.getOccupation() == null);
	}

	/**
	 * return vrai si les coins sont occupes
	 * 
	 * @param xColonie
	 * @param yColonie
	 * @return
	 */
	private boolean isVoisin(int xColonie, int yColonie) {
		boolean occupation = false;
		Coin coin = (Coin) daoCoin.findByXAndY(xColonie + 2, yColonie).orElse(null);
		if (coin != null && coin.getOccupation() != null) {
			occupation = true;
		}

		coin = (Coin) daoCoin.findByXAndY(xColonie - 2, yColonie).orElse(null);
		if (!occupation && coin != null && coin.getOccupation() != null) {
			occupation = true;
		}

		coin = (Coin) daoCoin.findByXAndY(xColonie, yColonie + 2).orElse(null);
		if (!occupation && coin != null && coin.getOccupation() != null) {
			occupation = true;
		}
		coin = (Coin) daoCoin.findByXAndY(xColonie, yColonie - 2).orElse(null);
		if (!occupation && coin != null && coin.getOccupation() != null) {
			occupation = true;
		}
		return occupation;
	}

	/**
	 * Premier tour permettant de s�lectionner une colonie
	 * La particularit� de ce premier tour est qu'il n'est pas n�cessaire que les routes
	 * soient voisine pour pouvoir placer une colonie
	 * @param x
	 * @param y
	 * @param j
	 * @return
	 */
	public boolean placerPremiereColonie(int x, int y, Joueur j) {
		boolean coinOk = true;
		if (isCoin(x, y)) {
			if (isLibre(x, y)) {
				if (!isVoisin(x, y)) {
					Coin leCoinSelectionne = (Coin) daoCoin.findByXAndY(x, y).orElse(null);
					leCoinSelectionne.setOccupation(j);
					daoCoin.save(leCoinSelectionne);
				} else {
					System.out.println("ERR : Il ya des colonies trop proches");
					coinOk = false;
				}
			} else {
				System.out.println("ERR : Ce coin est d�j� occup�");
				coinOk = false;
			}
		} else {
			System.out.println("ERR : Veuillez saisir un coin");
			coinOk = false;
		}
		return coinOk;
	}

	private boolean isColonieVoisineRoute(int x, int y, Joueur j) {
		boolean colonieVoisine = false;
		j = daoJoueur.findByIdFetchingPosition(j.getId()).orElse(null);
		if(j!= null) {
			long nbRoutesVoisines = j.getSegments()
			.stream()
			.filter(s -> (s.getX() == x - 1) || 
			s.getX() == (x + 1) ||
			s.getY() == (y-1) ||
			s.getY() == (y+1) ).count();
			if(nbRoutesVoisines >= 1) {
				colonieVoisine = true;
			}
		}
		return colonieVoisine;
	}
	
	
	/**
	 * Permet au joueur de placer une colonie adjecente � une de ses routes
	 * @param x
	 * @param y
	 * @param j
	 * @return
	 */
	public boolean placerUneColonie(int x, int y, Joueur j) {
		boolean coinOk = true;
		if (isCoin(x, y)) {
			if (isLibre(x, y)) {
				if (isColonieVoisineRoute(x, y, j)) {
					Coin leCoinSelectionne = (Coin) daoCoin.findByXAndY(x, y).orElse(null);
					leCoinSelectionne.setOccupation(j);
					daoCoin.save(leCoinSelectionne);
				} else {
					System.out.println("ERR : Veuillez saisir un coin");
					coinOk = false;
				}
			} else {
				System.out.println("ERR : Ce coin est deja occupe");
				coinOk = false;
			}
		} else {
			System.out.println("ERR : Aucune route n'est adjacente a ce coin");
			coinOk = false;
		}
		return coinOk;
	}

}
