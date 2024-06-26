package org.yanG.java.jdbc;

import org.yanG.java.jdbc.modelo.Producto;
import org.yanG.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.yanG.java.jdbc.repositorio.Repositorio;
import org.yanG.java.jdbc.util.ConexionBD_singleton;

import java.sql.Connection;
import java.sql.SQLException;

public class Main_JDBC_SingletonDelete {
    public static void main(String[] args) {
//El objeto Connetion se pone dentro del try para que haga autoclose de la conexión
        try(Connection conexion = ConexionBD_singleton.getInstance()) {

            System.out.println("==========Listamos=========");
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            //repositorio.Listar().forEach(System.out::println); forma lambda para imprimir
            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }

            System.out.println("========Buscamos por id===========");
            //buscamos el que tenga llave 2 y la L para indicar que es Long:
            System.out.println(repositorio.BuscarPorId(2L));

            System.out.println("========Actualizamos un producto==========");
            Producto producto = new Producto();
            producto.setId(3L);
            producto.setNombre("Teclado Razer");
            producto.setPrecio(700);
            repositorio.guardar(producto);
            System.out.println("Producto actualizado con exito");

            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }

            System.out.println("=========Eliminamos un producto==========");
            repositorio.eliminar(3l);
            System.out.println("Producto eliminado con exito");

            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
