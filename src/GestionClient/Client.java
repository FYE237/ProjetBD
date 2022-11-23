package GestionClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import SqlQUery.DBConnection;

public class Client {

	
	DBConnection dbConnection;
	private String login;
	private String password;
	
	public Client(DBConnection db) {
		this.dbConnection = db;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return un booléen pou savoir si l'authentification a réussi ou pas
	 */
	public boolean Authentification() {
		
		Scanner in = new Scanner(System.in);

		System.out.println("Login :  " );
		this.login = in.nextLine();
		
		
		System.out.println("Mot de passe :  " );
		this.password = in.nextLine();
		
		//System.out.println("You entered string " + login.concat(pwd));
		
		try {
			/*
			 * Requête SQL pour s'authentifier
			 */
			String cmd = "select * from  comptes  "; 
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement( cmd + " where NC = ? and NOM = ? ");

			stmt.setString(1, this.login);
			stmt.setString(2, this.password);
			
			ResultSet rset  = stmt.executeQuery();
			ResultSetMetaData rsetmd = rset.getMetaData();


			int i = rsetmd.getColumnCount();
			if(i == 0) return false;
			else return true;

		} catch ( SQLException e) {
			e. printStackTrace ();
			System.out.println("Echec : " + e.toString() + " \n" );
			return false;
		}
		
	}
	
	
}
