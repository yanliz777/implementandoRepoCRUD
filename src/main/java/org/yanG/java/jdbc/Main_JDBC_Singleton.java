package org.yanG.java.jdbc;

import org.yanG.java.jdbc.modelo.Producto;
import org.yanG.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.yanG.java.jdbc.repositorio.Repositorio;
import org.yanG.java.jdbc.util.ConexionBD_singleton;
import java.sql.*;

public class Main_JDBC_Singleton {
    public static void main(String[] args) {
//El objeto Connetion se pone dentro del try para que haga autoclose de la conexión
        try(Connection conexion = ConexionBD_singleton.getInstance()) {

            //listamos:
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            //repositorio.Listar().forEach(System.out::println); forma lambda para imprimir
            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }

            System.out.println("Buscamos por ID: ");
            //buscamos el que tenga llave 2 y la L para indicar que es Long:
            System.out.println(repositorio.BuscarPorId(2L));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
