package fr.colonscatane.exception;

public class IsVoisinTropProcheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Il ne doit pas y avoir de voisin a coté de la colonie";
	}
}
