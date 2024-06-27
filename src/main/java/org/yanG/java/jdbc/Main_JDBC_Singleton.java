package org.yanG.java.jdbc;

import org.yanG.java.jdbc.modelo.Categoria;
import org.yanG.java.jdbc.modelo.Producto;
import org.yanG.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.yanG.java.jdbc.repositorio.Repositorio;
import org.yanG.java.jdbc.util.ConexionBD_singleton;
import java.sql.*;
import java.util.Date;

public class Main_JDBC_Singleton {
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

            System.out.println("=========Creamos/insertamos nuevo producto==========");
            Producto producto = new Producto();
            producto.setNombre("Teclado Ryzer mecánico");
            producto.setPrecio(550);
            producto.setFecha_registro(new Date());//fecha creación de producto
            //el producto tiene que tener la categoria, como esta relacionado, siempre van juntos
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto guardado con exito");
            for (int i = 0; i < repositorio.Listar().size() ; i++) {
                System.out.println(repositorio.Listar().get(i));
            }

            System.out.println("=========Eliminamos un producto==========");




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
