package fr.colonscatane.views;

import com.fasterxml.jackson.annotation.JsonView;

@JsonView
public class Views {
	
	public static class Common{}
	
	public static class PositionPlateau extends Common{}
	
	public static class Coin extends PositionPlateau{}
	
	public static class Segment extends PositionPlateau{}
	
	public static class PositionPlateauWithJoueur extends PositionPlateau{}
	
	
	public static class Utilisateur extends Common{}
	
	public static class Joueur extends Utilisateur{}
	
	public static class JoueurWithPositionsPlateau extends Joueur{}

}
