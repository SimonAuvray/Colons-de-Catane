package fr.colonscatane.modele;

/**
 * 
 */
public abstract class PositionPlateau {


	protected int id;
	protected int x;
	protected int y;
	
	protected Joueur occupation = new Joueur();
	
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

	public Joueur getOccupation() {
		return occupation;
	}

	public void setOccupation(Joueur occupation) {
		this.occupation = occupation;
	}

	
    
    



}