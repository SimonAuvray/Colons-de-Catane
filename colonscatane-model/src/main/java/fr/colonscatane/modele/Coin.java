package fr.colonscatane.modele;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Coin extends PositionPlateau {

    public Coin() {
    }
    
    @ManyToMany
    @JoinTable(name = "LIENS",
    		joinColumns = @JoinColumn(name = "LIEN_COIN_ID", referencedColumnName = "POS_ID"),
    		inverseJoinColumns = @JoinColumn(name = "LIEN_TUILE_ID", referencedColumnName = "POS_ID"))
    public List<TuileRessource> ressources;
    
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