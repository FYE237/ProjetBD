import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import CategorieRestaurant.GestionCategorie;
import GestionClient.Client;
import Restaurant.GestionRestaurant;
import SqlQUery.DBConnection;


public class testJDBC {

	public testJDBC() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

			DBConnection dbconnection = new DBConnection();
			AfficherBienvenue();
			
			//Le client éssaie de se login
			Client client = new Client(dbconnection);
			
			while(! client.Authentification()) {
				
				System.out.println(" Identifiants incorrects ");
			}

			
			//S'il s'est login 
			Boolean connected = true;
			
			//Tant qu'il n'a pas demandé à se déconnecter
			while (connected) {
				
				//Entourer chacune de ses fonctions de try catch
				
				//On affiche la liste des catégories
				
				GestionCategorie gestionCategorie =  new GestionCategorie(dbconnection);
				gestionCategorie.afficherCategorire();
				
				//choix de la catégorie
				String idcategorie = gestionCategorie.getChoixCategorie();
				
				//On affiche la liste des restaurants par catégorie
				gestionCategorie.afficherRestaurantParCategorire(idcategorie);
				
				String idRestaurant = gestionCategorie.getChoixRestaurant();
				//On affiche les informations sur le restaurant choisi
				GestionRestaurant gestionRestaurant = new GestionRestaurant(dbconnection);
				gestionRestaurant.afficherRestaurant(idRestaurant);
				
				//On affiche les différents plat du restaurant
				gestionRestaurant.afficherPlatParRestaurant(idRestaurant);
				
				//choix du plat
				Map<Integer, Integer> idPLat = gestionRestaurant.getChoixPlat();
				
				/*
				 * On passe maintenant la commande.
				 */
				
			
			}
		
			
		
		
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
	public static void AfficherBienvenue() {
		Scanner in = new Scanner(System.in);
		Decorer();
		System.out.print("Bienvenue :) :)");
		Decorer();
		System.out.print("\n");
		
	}	

	


}


