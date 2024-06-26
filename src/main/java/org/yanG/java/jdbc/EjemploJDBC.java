package org.yanG.java.jdbc;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
        String username = "root";
        String password = "fRan@72412";
/*DriverManager Permite gestionar todos los drivers instalados en el sistema.
    Connection:Representa una conexión con una base de datos. Una aplicación puede tener
    más de una conexión a más de una base de datos.
    Statement: Permite ejecutar sentencias SQL sin parámetros
    ResultSet: Contiene las filas o registros obtenidos al ejecutar una sentencia SELECT
    con Statement
*/
        Connection conexion = null;
        Statement stmt = null;
        ResultSet resultado = null;

        try {
            conexion = DriverManager.getConnection(url,username,password);
            stmt = conexion.createStatement();
            resultado = stmt.executeQuery("SELECT * FROM productos");
/*
En el ciclo while(resultado.next()) recorremos todos los registros que traemos de
la consulta
 */
            while (resultado.next())
            {
                System.out.print(resultado.getInt("id"));
                System.out.print(" | ");
                System.out.print(resultado.getString("nombre"));
                System.out.print(" | ");
                System.out.print(resultado.getDouble("precio"));
                System.out.print(" | ");
                System.out.println(resultado.getDate("fecha_registro"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /*
            Es una buena practica ponerlo en el bloque de código del finally, ya que
            en caso de error en el try, saltaria al catch y por lo tanto no se cerraría
            las conexiones.
            Es buena practica cerrar las conexiones, empezando por la ultima que se abrio
            y terminado con la primera, así:
             */
            try {
                resultado.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
