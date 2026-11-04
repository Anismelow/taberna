package com.ipartek.taberna.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ipartek.taberna.modelo.Iva;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.repositorio.ProductoRepositorio;
import com.ipartek.taberna.servicio.ProductoServicio;

public class ProductoServicioImpl implements ProductoServicio{

    @Autowired 
    private ProductoRepositorio productoRepo;


    @Override 
    public List<Producto> obtenerTodos(){
        try{
            return productoRepo.findAll();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override 
    public Producto obtenerPorId(Integer id){
        try{
            return productoRepo.findById(id).orElse(new Producto());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Producto insertar(Producto producto){
        try{
            if(producto.getId() == null){
                return productoRepo.save(producto);
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }

    @Override
	public Producto modificar(Producto producto) {
		try {
			if (productoRepo.existsById(producto.getId())) {
				return productoRepo.save(producto);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    @Override
	public Boolean borrar(Integer id) {
		try {
			if (productoRepo.existsById(id)) {
				productoRepo.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    @Override
	public Boolean comprobarSiExiste(Integer id) {
		try {
			return productoRepo.existsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer contarRegistros() {
		try {
			return (int) productoRepo.count();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
