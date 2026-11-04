package com.ipartek.taberna.servicio;

import java.util.List;

import com.ipartek.taberna.modelo.Producto;

public interface ProductoServicio {

    public List<Producto> obtenerTOdos();
    public Producto obtenerPorId(Integer id);
    public Producto insertar(Producto producto);
    public Producto modificar(Producto producto);
    public Boolean borrar(Integer id);
    public Boolean comprobarSiExiste(Integer id);

    public Integer contarRegistros();

}
