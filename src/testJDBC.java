import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

import CategorieRestaurant.GestionCategorie;
import GestionClient.Client;
import SqlQUery.DBConnection;


public class testJDBC {

	public testJDBC() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//			
//		// Enter data using BufferReader
//        BufferedReader reader = new BufferedReader(
//            new InputStreamReader(System.in));
// 
//        // Reading data using readLine
//        String name = reader.readLine();
//		System.out.println(name);
			
			DBConnection dbconnection = new DBConnection();
			AfficherMenu();
			
			//Le client éssaie de se login
			Client client = new Client(dbconnection);
			
			while(! client.Authentification()) {
				System.out.println(" Identifiants incorrects ");
			}

			
			//Si il se login avec succès
			//On affiche la liste des catégories
			
			GestionCategorie gestionCategorie =  new GestionCategorie(dbconnection);
			
		AfficherListeRestaurantParCategrorie(dbconnection);
			
		
			
		
		
		try {
			dbconnection.getConnection().close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Echec : " + e.toString() + " \n" );
			
		}
		

	}
	
	public static void Decorer() {
		int i =0;
		while(i < 10) {
			System.out.print("*");
			i++;
		}
	}
	
	/**
	 * Afficher le menu  à l'utilisateur dès sa connexion
	 * @param dbConnection
	 */
	public static void AfficherMenu() {
		Scanner in = new Scanner(System.in);
		Decorer();
		System.out.print("Bienvenue :) :)");
		Decorer();
		System.out.print("\n");
		
		
	}
	
//	/**
//	 * 
//	 * @param dbConnection
//	 * @return un booleen qui indique si l'authentificationa réussie
//	 */
//	public static boolean authentification(DBConnection dbConnection) {
//		
//		Scanner in = new Scanner(System.in);
//
//		System.out.println("Login :  " );
//		String login = in.nextLine();
//		
//		System.out.println("Mot de passe :  " );
//		String pwd = in.nextLine();
//		
//		//System.out.println("You entered string " + login.concat(pwd));
//		
//		try {
//			/*
//			 * Requête SQL pour s'authentifier
//			 */
//			String cmd = "select * from  comptes  "; 
//			PreparedStatement stmt = dbConnection.getConnection().prepareStatement( cmd + " where NC = ? and NOM = ? ");
//
//			stmt.setString(1, login);
//			stmt.setString(2, pwd);
//			
//			ResultSet rset  = stmt.executeQuery();
//			ResultSetMetaData rsetmd = rset.getMetaData();
//
//
//			int i = rsetmd.getColumnCount();
//			if(i == 0) return false;
//			else return true;
//
//		} catch ( SQLException e) {
//			e. printStackTrace ();
//			System.out.println("Echec : " + e.toString() + " \n" );
//			return false;
//		}
//
//
//	}
	
	/**
	 * Affichage de la liste des restaurants par catégorie
	 * @param dbconnection
	 */
	public static void AfficherListeRestaurantParCategrorie(DBConnection dbconnection) {
		try {
			
			String cmd = "select * from ";
			PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd + " comptes ");
			
			ResultSet rset  = stmt.executeQuery();
			ResultSetMetaData rsetmd = rset.getMetaData();
			
			
			int i = rsetmd.getColumnCount();
			if(i == 0) System.out.println("Aucun élement present");
			System.out.print("NC" + "\t" + "NOM" + "\t" + "SOLDE" + "\n");
			 while (rset.next()) {
		            for (int j = 1; j <= i; j++) {
		                System.out.print(rset.getString(j) + "\t");
			    }
			    System.out.println();
		        }
			
			} catch ( SQLException e) {
				e. printStackTrace ();
				System.out.println("Echec : " + e.toString() + " \n" );
			}
		
	}

}


