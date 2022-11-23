package SqlQUery;

import  java.sql.*;

public class DBConnection {

	Connection connection;
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "ondouaa";
    static final String PASSWD = "ondouaa";
	public DBConnection() {
		// TODO Auto-generated constructor stub
		
	try {
		// Enregistrement du driver Oracle
        System.out.print("Loading Oracle driver... "); 
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println("loaded");


        // Etablissement de la connection
        System.out.print("Connecting to the database... ");  
        this.connection = DriverManager.getConnection(CONN_URL, USER, PASSWD);
        System.out.println("connected");
       
	
	
		} catch (SQLException e) {
			// TODO: handle exception
			e. printStackTrace ();
			System.out.println("Echec : " + e.toString() + " \n" );

		}

	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	
	
	
}
