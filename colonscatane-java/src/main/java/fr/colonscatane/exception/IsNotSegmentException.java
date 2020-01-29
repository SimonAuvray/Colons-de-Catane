package fr.colonscatane.exception;

public class IsNotSegmentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Les coordonnees ne correspondent pas a une route";
	}
}
