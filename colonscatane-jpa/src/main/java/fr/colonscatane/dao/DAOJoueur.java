package fr.colonscatane.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;

public class DAOJoueur extends ConnectionSQL implements IDAOJoueur {

	@Override
	public List<Joueur> findAll() {
		List<Joueur> mesJoueurs = new ArrayList<Joueur>();
		
		try {
			Statement myStatement = null;
			myStatement = myConnection.createStatement();
			ResultSet myResult = myStatement.executeQuery("SELECT * FROM JOUEUR");
			
			while (myResult.next()) {
				Joueur joueur = new Joueur();
				joueur.setNom(myResult.getString("nom"));
				joueur.setCouleur(Couleur.valueOf(myResult.getString("couleur")));
				joueur.setBoisPossede(myResult.getInt("bois_possede"));
				joueur.setArgilePossede(myResult.getInt("argile_possede"));
				joueur.setMoutonPossede(myResult.getInt("mouton_possede"));
				joueur.setBlePossede(myResult.getInt("ble_possede"));
				joueur.setPierrePossede(myResult.getInt("pierre_possede"));
				joueur.setCompteurColonie(myResult.getInt("compteur_colonie"));
				joueur.setCompteurVille(myResult.getInt("compteur_ville"));
				joueur.setCompteurRoute(myResult.getInt("compteur_route"));
				joueur.setScore(myResult.getInt("score"));
				
				mesJoueurs.add(joueur);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERR : Erreur lors de la recherche de la liste de joueurs");
		}
		
		return mesJoueurs;
	}
	
	
	

	
	

	@Override
	public Joueur save(Joueur entity) {
		PreparedStatement myPrepStatementSave = null;
		
		
		try {
			PreparedStatement myPrepStatementCouleur = null;
			
			myPrepStatementCouleur = myConnection.prepareStatement ("SELECT couleur FROM joueur WHERE couleur = ?");
			
			myPrepStatementCouleur.setString(1, entity.getNomCouleur());
			
			ResultSet myResult = myPrepStatementCouleur.executeQuery();
			
			
			
			
			if (myResult == null || !myResult.next()) {
				System.out.println("insertion");
				myPrepStatementSave = myConnection.prepareStatement("INSERT INTO Joueur (nom, couleur, bois_possede, ble_possede, argile_possede, pierre_possede, mouton_possede, compteur_colonie, compteur_ville, compteur_route, score) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				myPrepStatementSave.setString(1, entity.getNom());
				myPrepStatementSave.setString(2, entity.getNomCouleur());
				myPrepStatementSave.setInt(3, entity.getBoisPossede());
				myPrepStatementSave.setInt(4, entity.getBlePossede());
				myPrepStatementSave.setInt(5, entity.getArgilePossede());
				myPrepStatementSave.setInt(6, entity.getPierrePossede());
				myPrepStatementSave.setInt(7, entity.getMoutonPossede());
				myPrepStatementSave.setInt(8, entity.getCompteurColonie());
				myPrepStatementSave.setInt(9, entity.getCompteurVille());
				myPrepStatementSave.setInt(10, entity.getCompteurRoute());
				myPrepStatementSave.setInt(11, entity.getScore());
				
				myPrepStatementSave.execute();
				
				
			}
			
			else {
				System.out.println("modification");
				myPrepStatementSave = myConnection.prepareStatement("UPDATE JOUEUR  "
						+ "SET bois_possede = ?, ble_possede = ?, argile_possede = ?, pierre_possede = ?, mouton_possede = ?, compteur_colonie = ?, compteur_ville = ?, compteur_route = ?, score = ?, nom = ? " +
						" WHERE couleur = ?");
				
				
				myPrepStatementSave.setInt(1, entity.getBoisPossede());
				myPrepStatementSave.setInt(2, entity.getBlePossede());
				myPrepStatementSave.setInt(3, entity.getArgilePossede());
				myPrepStatementSave.setInt(4, entity.getPierrePossede());
				myPrepStatementSave.setInt(5, entity.getMoutonPossede());
				myPrepStatementSave.setInt(6, entity.getCompteurColonie());
				myPrepStatementSave.setInt(7, entity.getCompteurVille());
				myPrepStatementSave.setInt(8, entity.getCompteurRoute());
				myPrepStatementSave.setInt(9, entity.getScore());
				myPrepStatementSave.setString(10, entity.getNom());
				myPrepStatementSave.setString(11, entity.getNomCouleur());
				
				myPrepStatementSave.execute();
				
				
				
				
			}
			
		}
		
		
		catch (SQLException e) {
			System.out.println("Une erreur s'est produite dans SQL");
			e.printStackTrace();
		}
		
		catch (Exception e) {
			System.out.println("Une erreur s'est produite en dehors de SQL");
		}
		
		return entity;
		
	}

	@Override
	public void delete(Joueur entity) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public Joueur findById(Couleur id) {
		
		Joueur joueur = new Joueur();
		
		try {
			PreparedStatement pStatement = null;
			pStatement = myConnection.prepareStatement(
					"SELECT * FROM Joueur where couleur = ?");
			pStatement.setString(1, String.valueOf(id));
			System.out.println(String.valueOf(id));
			ResultSet myResult = pStatement.executeQuery();
			while (myResult.next()) {
				joueur.setCouleur(Couleur.valueOf(myResult.getString("couleur")));
				joueur.setNom(myResult.getString("nom"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERR : Erreur lors de la recherche du joueur");
		}
		
		return joueur;
	}







	@Override
	public void deleteById(Couleur id) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void deleteAll() {
		String requeteSQL = "DELETE FROM joueur";
		try {
		Statement stmt = myConnection.createStatement();
		stmt.execute(requeteSQL);
		}catch(SQLException e) {
			System.out.println("ERR : Erreur lors de l'exécution de la requête "+requeteSQL);
		}
		
	}

	



	
	
	
	

}
