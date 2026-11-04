package com.ipartek.taberna.servicio;

import java.util.List;

import com.ipartek.taberna.modelo.Categoria;

public interface CategoriaServicio {
    public List<Categoria> obtenerTodos();
    public Categoria obtenerPorId(Integer id);
    public Categoria insertar(Categoria categoria);
    public Categoria modificar(Categoria categoria);
    public Boolean borrar(Integer id);

    public Boolean comprobarSiExiste(Integer id);
    public Integer contarRegistros();
    public void resetearAutoIncrement();
    public void insertarConId(Categoria categoria);
}
