package Restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SqlQUery.DBConnection;

public class GestionRestaurant {

	private DBConnection dbconnection;//Connection  à la base de donnée
	private List<Integer>listePlat; // L'id de la table plat est un type number
	private Map<Integer,Integer> listePlatCommande ;// La liste des plats de la commande d'un client et les quantités
	
	public GestionRestaurant(DBConnection dbc) {
		// TODO Auto-generated constructor stub
		dbconnection = dbc;
		listePlat = new ArrayList<Integer>();
		listePlatCommande = new HashMap<>();
	}
	
	/**
	 * Afficher les informations sur un restaurant
	 * @param idRestauant
	 * idRestaurant c'est l'id du restaurant dont on veut afficher les informations
	 */	
	public void afficherRestaurant(String idRestauant) {
		try {
			
				//Il faudra corriger cela lorsque la ta
				//Requête pour afficher les informations d'un restaurant 
				String cmd = "SELECT * FROM RESTAURANTS \n"
						+ "JOIN HORAIRES\n"
						+ "ON RESTAURANTS.EMAIL = HORAIRES.EMAILRESTAURANT\n"
						+ "JOIN CATEGORIESRESTAURANTS\n"
						+ "ON RESTAURANTS.EMAIL = CATEGORIESRESTAURANTS.EMAILRESTAURANT\n"
							
				//A décommenter. Je commente cette partie pour le moment car la table tycommande est vide
//				+ "JOIN TYPECOMMANDES\n"
//				+ "ON RESTAURANTS.EMAIL = TYPECOMMANDES.EMAILRESTAURANT"

						+ "where RESTAURANTS.email = ? ";

								
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				stmt.setString(1, idRestauant);
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 
					{	
						System.out.print(" \t Détails des informatios du restaurant " + idRestauant + " : \n\n");
						
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
						System.out.println();
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
				String cmd = "select NOM, DESCRIPTION ,PRIX, ALLERGENES.NOM "
						+ " from plats join  platsrestaurants on "
								+ "plats.idplat = platsrestaurants.idplat "
								+ "JOIN ALLERGERNES"
								+ "ON  PLATS.IdPlat = ALLERGERNES.IdPlat"
								+ "where emailrestaurant = ? ";
				
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				stmt.setString(1, email);
				
				ResultSet rset  = stmt.executeQuery();
				
				
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(!rset.isBeforeFirst()) System.out.println("Aucun élement present \n");
				else 
					{	
						System.out.print(" \t La liste des plats du restaurant :  " + email + " : \n");
						
						System.out.print("NOM" + "\t" + "DESCRIPTION" + "\t" + "PRIX" + "\t" + "ALLERGENES" + "\t" + "\n");
						int k = 0;
						while (rset.next()) {
								System.out.print( rset.getInt(1) + "\t");
							 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
							// On commence à deux car on ne veut pas afficher l'id des plats dans notre BD
					            for (int j = 2; j <= i; j++) {
					                System.out.print( rset.getString(j) + "\t");
					         
					            }
					            //l'id du plat c'est la colonne 1 
								   listePlat.add(rset.getInt(1));
							        //k++;
								    System.out.println();
					     }
						System.out.println();
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
	public Map<Integer,Integer> getChoixPlat() throws IOException {
		
			/// Enter data using BufferReader
	      BufferedReader reader = new BufferedReader(
	          new InputStreamReader(System.in));
	      
	      String choix = "z";
	      String qte = "z" ;
	      boolean fini = false;
	      while(!fini) {
	    	  fini = true;
		      while (!choix.matches("[1-9]+")) {
		    	  System.out.println("Quel plat désirez vous ? \n");
		          
		          // Reading data using readLine
		    	  choix = reader.readLine();
		    	  
		    	  qte = "z";
		    	  while(!qte.matches("[1-9]+")) {
		    		  System.out.println("Combien de plats ? \n");
		    		  qte = reader.readLine();
		    	  }
		      }
		      listePlatCommande.put(Integer.parseInt(choix),Integer.parseInt(qte));
		      choix = "z";
		      while(! choix.matches("Y|y|N|n")) {
		    	  System.out.println("Souhaitez - vous autre chose ?[Y/N] \n");
		    	// Reading data using readLine
		    	  choix = reader.readLine();
		      }
		      if(choix.matches("Y|y")) fini = false;
	     }
	      
	      return listePlatCommande;
	}

}
