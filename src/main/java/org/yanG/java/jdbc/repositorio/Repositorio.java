package org.yanG.java.jdbc.repositorio;

import java.util.List;

//Esta interface Repositorio, se puede utilizar para
//cualquier objeto ya que es generica.

public interface Repositorio<T> {
    List<T> Listar();

    T BuscarPorId(Long id);

    //Dentro de este método vamos a tener el insert y el update
    void guardar(T objeto);

    //Siempre se elimina por la llave primaria:
    void eliminar(Long id);
}
