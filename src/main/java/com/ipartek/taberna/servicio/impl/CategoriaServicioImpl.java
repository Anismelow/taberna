package com.ipartek.taberna.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.taberna.repositorio.CategoriaRepositorio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.modelo.Categoria;


@Service 
public class CategoriaServicioImpl implements CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepo;

    @Override
    public List<Categoria> obtenerTodos() {
        try {
            return categoriaRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Categoria obtenerPorId(Integer id) {
        try {
            return categoriaRepo.findById(id).orElse(new Categoria());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Categoria insertar(Categoria categoria) {
        try {
            if (categoria.getId() == null) {
                return categoriaRepo.save(categoria);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Categoria modificar(Categoria categoria) {
        try {
            if (categoriaRepo.existsById(categoria.getId())) {
                return categoriaRepo.save(categoria);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override 
    public Boolean borrar(Integer id){
        try {
            if(categoriaRepo.existsById(id)){
                categoriaRepo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer contarRegistros(){
        try{
            return(int) categoriaRepo.count();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override 
    public void resetearAutoIncrement(){
        try{
            categoriaRepo.resetearAutoInc();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override 
    public void insertarConId(Categoria categoria){
        try{
            categoriaRepo.insertarConId(categoria.getId(),categoria.getNombre());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
	public Boolean comprobarSiExiste(Integer id) {
		try {
			return categoriaRepo.existsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
