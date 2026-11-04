package com.ipartek.taberna.servicio;

import java.util.List;

import com.ipartek.taberna.modelo.Alergeno;

public interface AlergenoServicio {

    public List<Alergeno> obtenerTodos();
    public Alergeno obtenerPorId(Integer id);
    public Alergeno insertar(Alergeno alergeno);
    public Alergeno modificar(Alergeno alergeno);
    public Boolean borrar(Integer id);
    public Boolean comprobarSiExiste(Integer id);

    public Integer contarRegistros();
	public void resetearAutoIncrement();
	public void insertarConId(Alergeno alergeno);

}
