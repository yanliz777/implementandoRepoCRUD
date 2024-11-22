package org.yanG.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD_singleton {

    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "fRan@72412";
    private static Connection connection;

    /*
    Metodo estatico getConecction para establecer la conexión y que solo haya una
    instancia de esta clase, es decir, una sola conexión a la BD para
    toda la aplicación. Solo se crea una vez este objeto:
    */
    public static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
}
