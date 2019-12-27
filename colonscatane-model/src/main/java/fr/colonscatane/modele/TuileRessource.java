/**
 * 
 */
package fr.colonscatane.modele;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("3")
public class TuileRessource extends PositionPlateau {
	
	@ManyToMany(mappedBy = "ressources")
	private List<Coin> listeCoin;
	
	@Column(name="TUILE_RESSOURCE")
	private TypeTuile typeRessource ;
	
	@Column(name="TUILE_NUMERO")
	private int numero;
	
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


	public List<Coin> getListeCoin() {
		return listeCoin;
	}


	public void setListeCoin(List<Coin> listeCoin) {
		this.listeCoin = listeCoin;
	}


	public TypeTuile getType() {
		return typeRessource;
	}


	public void setType(TypeTuile type) {
		this.typeRessource = type;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	
	
	
	

	
}