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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import fr.colonscatane.views.Views;

@Entity
@Table(name = "position_plateau")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "POS_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class PositionPlateau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POS_ID")
	@JsonView(Views.Common.class)
	protected int id;
	
	@JsonView(Views.PositionPlateau.class)
	protected int x;
	
	@JsonView(Views.PositionPlateau.class)
	protected int y;
	
	@ManyToOne
	@JoinColumn(name = "POS_JOUEUR", nullable=true)
	@JsonView(Views.PositionPlateau.class)
	protected Joueur occupation;
	
	@Column (name="POS_TYPE", insertable=false, updatable=false)
	private TypePosition type;
	
    public Joueur getOccupation() {
		return this.occupation;
	}

	public void setOccupation(Joueur occupation) {
		this.occupation = occupation;
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