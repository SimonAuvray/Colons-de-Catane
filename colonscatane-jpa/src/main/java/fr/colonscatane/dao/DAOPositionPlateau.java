/**
 * 
 */
package fr.colonscatane.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;

/**
 * @author aballenghien
 *
 */
public class DAOPositionPlateau extends ConnectionSQL implements IDAOPositionPlateau {

	@Override
	public List<PositionPlateau> findAll() {
		
		
		List<PositionPlateau> mesPositions= new ArrayList<PositionPlateau>();
		Joueur inoccupe = new Joueur ("innocupe");
		
		try {
			Statement myStatement = myConnection.createStatement();
			
			ResultSet myResult = myStatement.executeQuery("SELECT * FROM position_plateau p  LEFT JOIN joueur j  ON p.occupation = j.couleur");
			
			while (myResult.next()) {
				
				if (myResult.getString("type").equals("Coin")) {
					Coin monCoin = new Coin();
					monCoin.setId(myResult.getInt("id"));
					monCoin.setX(myResult.getInt("x"));
					monCoin.setY(myResult.getInt("y"));
					
					
					// définition du joueur
					
					if (myResult.getString("occupation") == null) {
						monCoin.setOccupation(inoccupe);
						
						mesPositions.add(monCoin);
						}
					
					else {
					
						Joueur monJoueur = new Joueur (Couleur.valueOf(myResult.getString("occupation")), myResult.getString("nom"));
						monCoin.setOccupation(monJoueur);
						
						
						mesPositions.add(monCoin);
						}
				}
				
					if (myResult.getString("type").equals("Segment")) {
						Segment monSegment= new Segment();
						monSegment.setId(myResult.getInt("id"));
						monSegment.setX(myResult.getInt("x"));
						monSegment.setY(myResult.getInt("y"));
						
						
						// définition du joueur
						
						if (myResult.getString("occupation") == null) {
							monSegment.setOccupation(inoccupe);
							
							mesPositions.add(monSegment);
						}
						
						else {
						
							Joueur monJoueur = new Joueur (Couleur.valueOf(myResult.getString("occupation")), myResult.getString("nom"));
							monSegment.setOccupation(monJoueur);
							
							
							mesPositions.add(monSegment);
						}
						
				}
			}
				
		
			
		}
		
		catch (SQLException e) {
			System.out.println("Une erreur s'est produite dans SQL find all");
			e.printStackTrace();
		}
		
		catch (Exception e) {
			System.out.println("Une erreur s'est produite en dehors de SQL");
			e.printStackTrace();
		}
		
		return mesPositions;
	}

	@Override
	public PositionPlateau findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PositionPlateau save(PositionPlateau entity) {
		try {
			PreparedStatement pStatement = null;
			String typePositionPlateau = entity.getClass().getSimpleName();
			// l'occupation par un joueur n'est pas connu au début du jeu
			if (entity.getId() == 0) {
				pStatement = myConnection.prepareStatement(
						"INSERT INTO position_plateau (x,y,type) VALUES (?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pStatement.setInt(1, entity.getX());
				pStatement.setInt(2, entity.getY());
				pStatement.setString(3,typePositionPlateau);

			} else {
				// en cours de partie, seule l'occupation peut être mise à jour
				pStatement = myConnection.prepareStatement(
						"UPDATE position_plateau SET occupation=? WHERE id=?",
						Statement.RETURN_GENERATED_KEYS);
				pStatement.setString(1, entity.getOccupation().getNomCouleur());
				pStatement.setInt(2, entity.getId());
			}
			pStatement.execute();
			ResultSet cleGenere = pStatement.getGeneratedKeys();
			if (cleGenere.next()) {
				entity.setId(cleGenere.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERR : Erreur lors de l'enregistrement de la position");
		}
		return entity;
	}

	@Override
	public void delete(PositionPlateau entity) {
		this.deleteById(entity.getId());
		
	}

	@Override
	public void deleteById(Integer id) {
		try {
			PreparedStatement myPrepStatement = myConnection.prepareStatement("DELETE FROM position_plateau WHERE id = ?");
			
			myPrepStatement.setInt(1, id);
			
			myPrepStatement.execute();
			
		}
		
		catch (SQLException e) {
			System.out.println("Erreur dans SQL");
		}
		
		catch (Exception e) {
			System.out.println("Erreur en dehors de SQL");
			
		}
			
			
		
	}

	@Override
	public PositionPlateau findByXY(int x, int y) {
		Joueur inoccupe = new Joueur ("innocupe");
		
		PositionPlateau pos = null;
		
		try {
		
			PreparedStatement myPrepStatement = myConnection.prepareStatement("SELECT * FROM position_plateau p LEFT JOIN joueur j ON p.occupation = j.couleur WHERE x = ? AND y = ?");
				
			myPrepStatement.setInt(1, x);
			myPrepStatement.setInt(2, y);
				
			ResultSet myResult =myPrepStatement.executeQuery();
			
			if(myResult.next()) {
				
						
				if (myResult.getString("type").equals("Coin")) {
					Coin monCoin = new Coin ();
					
					monCoin.setId(myResult.getInt("id"));
					monCoin.setX(myResult.getInt("x"));
					monCoin.setY(myResult.getInt("id"));
					
					// definition du joueur
					if (myResult.getString("occupation") == null) {
						monCoin.setOccupation(inoccupe);
						
						}
					
					else {
					
						Joueur monJoueur = new Joueur (Couleur.valueOf(myResult.getString("occupation")), myResult.getString("nom"));
						monCoin.setOccupation(monJoueur);

						}
					
					pos = monCoin;
					
					
				}
				
				if (myResult.getString("type").equals("Segment")) {
					Segment monSegment = new Segment ();
					
					monSegment.setId(myResult.getInt("id"));
					monSegment.setX(myResult.getInt("x"));
					monSegment.setY(myResult.getInt("id"));
					
					// definition du joueur
					if (myResult.getString("occupation") == null) {
						monSegment.setOccupation(inoccupe);
						
						}
					
					else {
					
						Joueur monJoueur = new Joueur (Couleur.valueOf(myResult.getString("occupation")), myResult.getString("nom"));
						monSegment.setOccupation(monJoueur);

						}
					
					pos = monSegment;
				}
				
				
				
			}
			else {System.out.println("Pas de position");}
			
		}
			
			catch (SQLException e) {
				System.out.println("Erreur dans SQL");
				e.printStackTrace();
			}
			
			catch (Exception e) {
				System.out.println("Erreur en dehors de SQL");
				
			}
		
		return pos;
			
			
			
			
	}

	@Override
	public void deleteAll() {
		String requeteSQL = "DELETE FROM position_plateau";
		try {
		Statement stmt = myConnection.createStatement();
		stmt.execute(requeteSQL);
		}catch(SQLException e) {
			System.out.println("ERR : Erreur lors de l'exécution de la requête "+requeteSQL);
		}
		
		
		requeteSQL = "ALTER TABLE position_plateau AUTO_INCREMENT = 1";
		try {
		Statement stmt = myConnection.createStatement();
		stmt.execute(requeteSQL);
		}catch(SQLException e) {
			System.out.println("ERR : Erreur lors de l'exécution de la requête "+requeteSQL);
		}
		
	}

	@Override
	public PositionPlateau findByType(PositionPlateau type) {
		// TODO Auto-generated method stub
		return null;
	}
}
