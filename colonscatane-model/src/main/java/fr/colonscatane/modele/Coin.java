package fr.colonscatane.modele;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

@Entity
@DiscriminatorValue("1")
public class Coin extends PositionPlateau {
	
	
	

    public List<TuileRessource> getRessources() {
		return ressources;
	}

	public void setRessources(List<TuileRessource> ressources) {
		this.ressources = ressources;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public Joueur getOccupationCoin() {
		return occupationCoin;
	}

	public void setOccupationCoin(Joueur occupationCoin) {
		this.occupationCoin = occupationCoin;
	}

	public Coin() {
    }
    
    @ManyToMany
    @JoinTable(name = "LIENS",
    		uniqueConstraints = @UniqueConstraint(columnNames = {"LIEN_COIN_ID", "LIEN_TUILE_ID"} ),
    		joinColumns = @JoinColumn(name = "LIEN_COIN_ID", referencedColumnName = "POS_ID"),
    		inverseJoinColumns = @JoinColumn(name = "LIEN_TUILE_ID", referencedColumnName = "POS_ID"))
    public List<TuileRessource> ressources;
    
    @Column(name = "TAILLE_OCCUPATION")
    public int taille;
    
    @ManyToOne
	@JoinColumn(name = "COIN_JOUEUR")
	protected Joueur occupationCoin;
	
//    /**
//     * 
//     */
//    private void rapporte() {
//        // TODO implement here
//    }

}