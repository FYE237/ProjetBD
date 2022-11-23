package Restaurant;

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

public class GestionRestaurant {

	private DBConnection dbconnection;//Connection  à la base de donnée
	private Map<Integer, Integer>listePlat; // L'id de la table plat est un type number
	
	public GestionRestaurant(DBConnection dbc) {
		// TODO Auto-generated constructor stub
		dbconnection = dbc;
		listePlat = new HashMap<Integer,Integer>();
	}
	
	/**
	 * Afficher les informations sur un restaurant
	 * @param idRestauant
	 * idRestaurant c'est l'id du restaurant dont on veut afficher les informations
	 */
	public void afficherRestaurant(String idRestauant) {
		try {
				//Requête pour afficher les informations d'un restaurant 
				String cmd = "select * from restaurants where email = ? ";
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				stmt.setString(1, idRestauant);
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 
					{	
						//System.out.print(" \t La liste des restaurants de la catégorie " + getChoixCategorie() + " : \n");
						
						System.out.print("Email" + "\t" + "NOM" + "\t" + "TELEPHONE" + "\t"+ "ADRESSE" + "\t" +
											"NOMBRE DE PLACE ASSISES" + "\t"+ "NOTE" + "\n");
						int k = 0;
						while (rset.next()) {

							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
					            for (int j = 1; j <= i; j++) {
					                System.out.print( rset.getString(j) + "\t");
					         
					            }

								    System.out.println();
					     }
					}
				
			} catch ( SQLException e) {
//				e. printStackTrace ();
				System.out.println("Echec : " + e.toString() + " \n" );
				System.out.println("Echec . Problème de Réseau  détails du restaurant \n" );

			}
	}
	
	
	
	/**
	 * FOnction qui affiche l'ensemble des plats d'un restaurant
	 * @param email 
	 * email = Restaurant.getChoixRestaurant(). C'est la clé primaire du restaurant sur lequel la 
	 * jointure avec Plat | platsRestaurants sera faite
	 */
	public void afficherPlatParRestaurant(String email) {
		try {
				//Requête pour afficher la liste des restaurants de la catégorie getCategorie() classée par évaluation
				String cmd = "";
				
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 
					{	
						//System.out.print(" \t La liste des restaurants de la catégorie " + getChoixCategorie() + " : \n");
						
						System.out.print("NOM" + "\t" + "DESCRIPTION" + "\t" + "PRIX" + "\t" +  "\n");
						int k = 0;
						while (rset.next()) {
								System.out.print( k + "\t");
							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
							// On commence à deux car on ne veut pas afficher l'id des plats dans notre BD
					            for (int j = 2; j <= i; j++) {
					                System.out.print( rset.getString(j) + "\t");
					         
					            }
					            //l'id du plat c'est la colonne 1 
								   listePlat.put(k, rset.getInt(1));
							        k++;
								    System.out.println();
					     }
					}
			
			
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			System.out.println("Echec : " + e.toString() + " \n" );

			System.out.println("Echec . Problème Plat \n" );

		}
		
	}
	
	/**
	 * On stocke le plat qui a été choisi par le client
	 * @return int n
	 * Elle renvoie l'id du plat que notre client aura choisie
	 * @throws IOException
	 */
	public int getChoixPlat() throws IOException {
		
			/// Enter data using BufferReader
	      BufferedReader reader = new BufferedReader(
	          new InputStreamReader(System.in));
	
	      String choix = "z";
	      while (!choix.matches("[0-9]+")) {
	    	  System.out.println("Quel plat désirez vous ? \n");
	          
	          // Reading data using readLine
	    	  choix = reader.readLine();
	      }
	      
	      return listePlat.get(Integer.parseInt(choix));
	}

}
