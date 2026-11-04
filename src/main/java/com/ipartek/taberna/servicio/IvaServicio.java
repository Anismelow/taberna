package com.ipartek.taberna.servicio;

import java.util.List;

import com.ipartek.taberna.modelo.Iva;

public interface IvaServicio {
    public List<Iva> obtenerTodos();
	public Iva obtenerPorId(Integer id);
	public Iva insertar(Iva iva);
	public Iva modificar(Iva iva);
	public Boolean borrar(Integer id);
	public Boolean comprobarSiExiste(Integer id);

	public Integer contarRegistros();
	public void resetearAutoIncrement();
	public void insertarConId(Iva iva);

}
