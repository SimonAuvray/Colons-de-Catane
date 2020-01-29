package fr.colonscatane.exception;

public class IsNotRouteVoisineException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Auncune colonie appartenant au joueur est adjacente à cette route";
	}
}
