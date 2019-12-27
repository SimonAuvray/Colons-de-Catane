package fr.colonscatane.modele;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "position_plateau")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "POS_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class PositionPlateau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POS_ID")
	protected int id;
	
	protected int x;
	protected int y;
	
	@Transient
	protected Joueur Occupation;
	
    public Joueur getOccupation() {
		return Occupation;
	}

	public void setOccupation(Joueur occupation) {
		Occupation = occupation;
	}

	/**
     * Default constructor
     */
    public PositionPlateau() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}



}