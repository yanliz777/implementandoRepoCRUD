package org.yanG.java.jdbc.repositorio;

import org.yanG.java.jdbc.modelo.Categoria;
import org.yanG.java.jdbc.modelo.Producto;
import org.yanG.java.jdbc.util.ConexionBD_singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
Implementamos la interface repositorio para hacer la operaciones
básicas en una BD(CRUD)
 */
public class ProductoRepositorioImpl implements Repositorio<Producto>{

    //metodo privado que devuelve la conexión a la bd:
    private Connection getConnection() throws SQLException {
        return ConexionBD_singleton.getInstance();
    }


    //metodo para listar:
    @Override
    public List<Producto> Listar() {
        List<Producto> productos = new ArrayList<>();
/*
Nota: es importante NO cerrar el recurso de la conexión a la bd. Por eso no cerramos
el objeto Connection ya que utilizamos un singleton y se cerraria la conxion al ser solo
una intancia en todo el programa,esta conexion se cierra al final de la aplicacion. Solo
indicamos que haga autoclose a lo que esta entre el parentesis del try():
 */
        try(Statement stmt = getConnection().createStatement();
//ResultSet Contiene las filas o registros obtenidos al ejecutar una sentencia SELECT
            ResultSet resultado = stmt.executeQuery("SELECT p.*,c.nombre AS nombre_categoria FROM " +
                    "productos AS p INNER JOIN categorias AS c ON (p.categoria_id = c.id)")) {
            /*
    En el ciclo while(resultado.next()) recorremos todos los registros que traemos de
    la consulta para settiar los datos*/
          while (resultado.next()) {
              Producto p = crearProducto(resultado);
//depués de settiar todos los atributos del objeto producto, lo agregamos a la lista
              productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productos;
    }

    //Método para buscar por id un producto(id de producto ya que producto contiene la categoria):
    @Override
    public Producto BuscarPorId(Long id) {
        Producto p = null;
//PreparedStatement: Permite ejecutar sentencias SQL con parámetros de entrada:
        try(PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT p.*,c.nombre AS nombre_categoria FROM " +
                        "productos AS p INNER JOIN categorias AS c " +
                        "ON (p.categoria_id = c.id) WHERE p.id = ?")) {

            //Pasamos el parámetro del id:
            stmt.setLong(1,id);
//ResultSet resultado = stmt.executeQuery() : devuelve solo un producto o ninguno
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    p = crearProducto(resultado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    //Dentro de este método vamos a tener el insert/crear y el update/actualizar que se le realiza a una BD
    @Override
    public void guardar(Producto objeto) {
        String sql;
        if (objeto.getId() != null && objeto.getId() > 0 )//para actualizar/update
        {
            sql = "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ? WHERE id = ?";
        }else //Para Insertar/crear el objeto
        {
            sql = "INSERT INTO productos(nombre, precio,categoria_id, fecha_registro)" +
                    "VALUES(?,?,?,?)";
        }
//PreparedStatement: Permite ejecutar sentencias SQL con parámetros de entrada:
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            //pasamos los parametros:
            stmt.setString(1,objeto.getNombre());
            stmt.setLong(2,objeto.getPrecio());
            stmt.setLong(3,objeto.getCategoria().getId());

            if (objeto.getId() != null && objeto.getId() > 0 )//para actualizar/update
            {
                stmt.setLong(4, objeto.getId());
            }else //Para Insertar/crear el objeto
            {
                stmt.setDate(4,new Date(objeto.getFecha_registro().getTime()));
            }
/*
El método executeUpdate() en JDBC (Java Database Connectivity) se utiliza para ejecutar sentencias SQL
que modifican la base de datos, como las sentencias INSERT, UPDATE, DELETE, y algunas operaciones
DDL (Data Definition Language) como CREATE TABLE y DROP TABLE.
 */
            stmt.executeUpdate();//ejecutamos la sentencia

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //método para eliminar un objeto de la BD
    @Override
    public void eliminar(Long id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id = ?")) {

            stmt.setLong(1,id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //método que se encarga de mapear/settiar los datos de la bd:
    private Producto crearProducto(ResultSet resultado) throws SQLException {

        Producto p = new Producto();
        p.setId(resultado.getLong("id"));
        p.setNombre(resultado.getString("nombre"));
        p.setPrecio(resultado.getInt("precio"));
        p.setFecha_registro(resultado.getDate("fecha_registro"));
        //objeto categoría: para relacionarlo con producto
        Categoria categoria = new Categoria();
        categoria.setId(resultado.getLong("categoria_id"));//como se pidío en la consulta
        categoria.setNombre(resultado.getString("nombre_categoria"));//como se pidío en la consulta
        p.setCategoria(categoria);
        return p;
    }
}
