package fr.colonscatane.exception;

public class IsOccupeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "La position est occup�e par un autre joueur";
	}
}
