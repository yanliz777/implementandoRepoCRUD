package org.yanG.java.jdbc;

import org.yanG.java.jdbc.modelo.Categoria;
import org.yanG.java.jdbc.modelo.Producto;
import org.yanG.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.yanG.java.jdbc.repositorio.Repositorio;
import org.yanG.java.jdbc.util.ConexionBD_singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Main_JDBC_SingletonUpdate {
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
            producto.setId(7L);
            producto.setNombre("Teclado Cosair k95 mecánico");//cambiamos nombre
            producto.setPrecio(300);
            //pasamos la categoria porque producto y categoria están relacionados
            // y podías haber un nullpointer exption:
            Categoria categoria = new Categoria();
            categoria.setId(2L);//cambia de categoria
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto actualizado con exito");

            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }

            System.out.println("=========Eliminamos un producto==========");
            



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
