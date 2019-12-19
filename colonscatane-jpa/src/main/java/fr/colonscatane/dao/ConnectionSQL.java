package fr.colonscatane.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSQL {
	
	protected static Connection myConnection = null;
	
	
	public ConnectionSQL () {
		this.setConnection();
	}
	
	
	
	
	public void setConnection() {
		
		try {
			if (myConnection == null ) {
				myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/colon_catane?serverTimezone=UTC", "root", "lien44=lien44");
			}
			
		}
		
		catch(Exception e) {
			System.out.println("Erreur lors de la connexion");
			e.printStackTrace();
		}
		
	}
		
		
			
		
		

}
	
	
	
