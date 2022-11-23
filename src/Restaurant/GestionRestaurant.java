package Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import SqlQUery.DBConnection;

public class GestionRestaurant {

	private DBConnection dbconnection;//Connection  à la base de donnée
	private Map<Integer, String>listePlat;
	
	public GestionRestaurant(DBConnection dbc) {
		// TODO Auto-generated constructor stub
		dbconnection = dbc;
		listePlat = new HashMap<Integer,String>();
	}
	
	
	public void afficherRestaurant() {
		try {
				//On affiche les informations d'un restaurant 
				String cmd = "";
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(i == 0) System.out.println("Aucun élement present");
				System.out.print("NC" + "\t" + "NOM" + "\t" + "\n");
				int k = 0;
				 while (rset.next()) {
			            //for (int j = 1; j <= i; j++) {
			                System.out.print( k + "\t" +rset.getString(2) + "\n");
			            
					        listePlat.put(k, rset.getString(2));
					        k++;
						    System.out.println();
			            //}
			        }
				
			} catch ( SQLException e) {
//				e. printStackTrace ();
				System.out.println("Echec : " + e.toString() + " \n" );
				System.out.println("Echec . Problème de Réseau  \n" );

			}
	}
	
	public String getChoixCategorie() {
		String  n = System.console().readLine();
		return listePlat.get(Integer.parseInt(n));
		
	}
	
	
	/**
	 * On affiche les restaurants de la catégorie choisie par ordre
	 * d'évaluation décroissante
	 */
	public void afficherPlatParRestaurant() {
		try {
				//Requête pour afficher la liste des restaurants de la catégorie getCategorie() classée par évaluation
				String cmd = "SELECT ";
				
				PreparedStatement stmt = dbconnection.getConnection().prepareStatement( cmd );
				ResultSet rset  = stmt.executeQuery();
				ResultSetMetaData rsetmd = rset.getMetaData();
				
				
				int i = rsetmd.getColumnCount();
				if(i == 0) System.out.println("Aucun élement present");
				System.out.print("NC" + "\t" + "NOM" + "\t" + "\n");
				int k = 0;
				 while (rset.next()) {
					 //On associe à chaque tuple du résultat un couple numéro et valeur cléprimaire de l'attribut
			            for (int j = 1; j <= i; j++) {
			                System.out.print( k + "\t" +rset.getString(2) + "\n");
			            
					        listePlat.put(k, rset.getString(2));
					        k++;
						    System.out.println();
			            }
			     }
			
			
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			System.out.println("Echec : " + e.toString() + " \n" );

			System.out.println("Echec . Problème de Réseau  \n" );

		}
		
	}

}
