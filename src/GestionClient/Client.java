package GestionClient;

import java.io.Console;
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
		
		Console console = System.console();
		
		Scanner in = new Scanner(System.in);

		System.out.println("Login :  " );
		this.login = in.nextLine();
		
		
//		System.out.println("Mot de passe :  " );
//		this.password = in.nextLine();
		
//		System.out.println("Mot de passe :  " );
		this.password = new String(console.readPassword("Mot de passe :  "));
		
		//System.out.println("You entered string " + login.concat(pwd));
		
		try {
			/*
			 * Requête SQL pour s'authentifier
			 */
			
			System.out.println("Login :  " + login );
			String cmd = "SELECT * FROM COMPTES WHERE EMAIL = ? and PSWD = ? "; 
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement( cmd);

			stmt.setString(1, this.login);
			stmt.setString(2, this.password);
			
			
			ResultSet rset  = stmt.executeQuery();

			if(!rset.isBeforeFirst()) {
				System.out.println("Echec Connexion  \n" );
				return false;
			}
			else return true;

		} catch ( SQLException e) {
//			e. printStackTrace ();
			System.out.println("Echec Connexion  \n" );
			return false;
		}
		
	}
	
	
}
