package CategorieRestaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	/**
	 * On affiche la liste des catégories
	 */
	public void afficherCategorire() {
		try {
				//Commande pour afficher la liste des categories de restaurant
				String cmd = "select * from categories";
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 	
					{	
						//System.out.print(" \t La liste des restaurants de la catégorie " + getChoixCategorie() + " : \n");
						
						System.out.print(" \t NOM CATEGORIE" + "\t" + "CATEGORIE MERE" + "\t" + "\n");
						int k = 0;
						while (rset.next()) {
							System.out.print( k + "\t");
							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
					        for (int j = 1; j <= i; j++) {
					            System.out.print("\t" +rset.getString(j) + "\t");         
					        }
							listeCategorie.put(k, rset.getString(1));
							k++;
							System.out.println();
					     }
					}
				
			} catch ( SQLException e) {
				System.out.println("Echec : " + e.toString() + " \n" );
			}
	}
	
	/*
	 * On lit la valeur choisie par l'utilisateur pour afficher la liste des catégories
	 */
	public String getChoixCategorie() throws IOException {
		/// Enter data using BufferReader
	      BufferedReader reader = new BufferedReader(
	          new InputStreamReader(System.in));

	      String choix = "z";
	      while (!choix.matches("[0-9]+")) {
	    	  System.out.println("Quelle catégorie désirez vous ? \n");
	          
	          // Reading data using readLine
	    	  choix = reader.readLine();
		}
		String tmp  = listeCategorie.get(Integer.parseInt(choix));		
		return tmp;
		
	}
	
	public String getHour() {
		String res = java.time.LocalTime.now().toString().substring(0, 1);
		int tmp = Integer.parseInt(res);
		if(tmp >= 12 && tmp <= 14) return "12H-14H";
		if(tmp >= 19 && tmp <= 23) return "19H-23H";
 		return "5";
	}
	
	public String getDay() {
		String res = new java.util.Date().toString().substring(0, 2);
		if(res.toUpperCase().equals("MON")) return "LUNDI";
		if(res.toUpperCase().equals("TUE")) return "MARDI";
		if(res.toUpperCase().equals("WED")) return "MERCREDI";
		if(res.toUpperCase().equals("THU")) return "JEUDI";
		if(res.toUpperCase().equals("FRI")) return "VENDREDI";
		if(res.toUpperCase().equals("SAT")) return "SAMEDI";
		if(res.toUpperCase().equals("SUN")) return "DIMANCHE";
		
		return "";
	}
	
	/**
	 * On affiche les restaurants de la catégorie choisie par ordre
	 * d'évaluation décroissante
	 * @param categorie = getChoixCategoire
	 */
	public void afficherRestaurantParCategorire(String categorie) {
		try {
				listeCategorie = new HashMap<Integer,String>();
				//Requête pour afficher la liste des restaurants de la catégorie getCategorie() classée par évaluation
				String cmd = "select emailrestaurant , nom , adresse from categoriesrestaurants JOIN restaurants "
						+ "on emailrestaurant = email join horaires on"
						+ " horaires.emailrestaurant = restaurant.emailrestaurant"
						+ "where  nomcategorie = ?  and plagehoraire = ?  and jour = ? ";

				
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				stmt.setString(1, categorie);
				stmt.setString(2, getHour());
				stmt.setString(3, getDay());
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 
					{	

						System.out.print(" \t La liste des restaurants de la catégorie " + categorie + " : \n\n");
						
						System.out.print("EMAIL DU RESTAURANT" + "\t" + "NOM" + "\t" + "ADRESSE" + "\n");
						int k = 0;
						while (rset.next()) {
								System.out.print( k + "\t");
							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
					            for (int j = 1; j <= i; j++) {
					                System.out.print( "\t" +rset.getString(j) + "\t");
					         
					            }
								   listeCategorie.put(k, rset.getString(1));
							        k++;
								    System.out.println();
					     }
						
						System.out.println();
					}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Echec : " + e.toString() + " \n" );
			System.out.println("Echec . Problème de Réseau  \n" );
		}
		
	}
	
	
	public String getChoixRestaurant() throws IOException {
		/// Enter data using BufferReader
	      BufferedReader reader = new BufferedReader(
	          new InputStreamReader(System.in));

	      String choix = "z";
	      while (!choix.matches("[0-9]+")) {
	    	  System.out.println("Quel restaurant choisissez vous ? \n");
	          
	          // Reading data using readLine
	    	  choix = reader.readLine();
		}
		String tmp  = listeCategorie.get(Integer.parseInt(choix));		
		return tmp;
		
	}
	
	

}
