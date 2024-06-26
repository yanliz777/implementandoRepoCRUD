package org.yanG.java.jdbc;

import java.sql.*;

public class EjemploJDBC_mejorado {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
        String username = "root";
        String password = "fRan@72412";
/*
Poniendo Connection conexion,Statement stmt y ResultSet resultado dentro de los parentesis del try()
se hace un autoclose de las conexiones, es una mejora con el otro ejemplo que teníamos
 */
        try( Connection conexion = DriverManager.getConnection(url, username, password);
        Statement stmt = conexion.createStatement();
        ResultSet resultado = stmt.executeQuery("SELECT * FROM productos")) {
/*
En el ciclo while(resultado.next()) recorremos todos los registros que traemos de
la consulta
 */         while (resultado.next()) {
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
        }
    }
}
