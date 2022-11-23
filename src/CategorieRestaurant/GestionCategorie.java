package CategorieRestaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import SqlQUery.DBConnection;

public class GestionCategorie {

	private DBConnection dbconnection;//Connection  à la base de donnée
	private Map<Integer, String>listeCategorie;
	
	public GestionCategorie(DBConnection dbc) {
		// TODO Auto-generated constructor stub
		dbconnection = dbc;
		listeCategorie = new HashMap<Integer,String>();
	}
	
	
	public void afficherCategorire() {
		try {
				//Commande pour afficher la liste des categories de restaurant
				String cmd = "select NC , NOM  from comptes";
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(i == 0) System.out.println("Aucun élement present");
				else
				{	
					System.out.print(" \t Liste des catégories de restaurant disponibles : \n");
					System.out.print("NC" + "\t" + "NOM" + "\t" + "\n");
				
					int k = 0;
					 while (rset.next()) {
				            //for (int j = 1; j <= i; j++) {
				                System.out.print( k + "\t" +rset.getString(2) + "\n");
				            
						        listeCategorie.put(k, rset.getString(2));
						        k++;
							    System.out.println();
				            //}
				        }
				}
				
			} catch ( SQLException e) {
				System.out.println("Echec : " + e.toString() + " \n" );
			}
	}
	
	/*
	 * On lit la valeur choisie par l'utilisateur pour afficher la liste des catégories
	 */
	public String getChoixCategorie() {
		String  n = "z";
		while(! n.matches("[0-9]+")) {
			System.out.print("Faites votre choix :\t");
			n = System.console().readLine();
		}
		
		return listeCategorie.get(Integer.parseInt(n));
		
	}
	
	
	/**
	 * On affiche les restaurants de la catégorie choisie par ordre
	 * d'évaluation décroissante
	 */
	public void afficherRestaurantParCategorire() {
		try {
				//Requête pour afficher la liste des restaurants de la catégorie getCategorie() classée par évaluation
				String cmd = "SELECT ";
				
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(i == 0) System.out.println("Aucun élement present \n");
				else 
					{	
						System.out.print(" \t La liste des restaurants de la catégorie " + getChoixCategorie() + " : \n");
						
						//System.out.print("NC" + "\t" + "NOM" + "\t" + "\n");
						int k = 0;
						while (rset.next()) {
							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
					            for (int j = 1; j <= i; j++) {
					                System.out.print( k + "\t" +rset.getString(2) + "\n");
					            
							        listeCategorie.put(k, rset.getString(2));
							        k++;
								    System.out.println();
					            }
					     }
					}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Echec : " + e.toString() + " \n" );
			System.out.println("Echec . Problème de Réseau  \n" );
		}
		
	}

}
