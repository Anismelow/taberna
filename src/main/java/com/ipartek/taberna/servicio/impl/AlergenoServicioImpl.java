package com.ipartek.taberna.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ipartek.taberna.modelo.Alergeno;
import com.ipartek.taberna.repositorio.AlergenoRepositorio;
import com.ipartek.taberna.servicio.AlergenoServicio;

public class AlergenoServicioImpl implements  AlergenoServicio{

    @Autowired 
    private AlergenoRepositorio alergenoRepo;

    @Override 
    public List<Alergeno> obtenerTodos(){
        try{
            return alergenoRepo.findAll();

        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public Alergeno obtenerPorId(Integer Id){
        try{
            return alergenoRepo.findById(Id).orElse(new Alergeno());
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public Alergeno insertar(Alergeno alergeno){
        try{
            if(alergeno.getId() == null){
                return alergenoRepo.save(alergeno);
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
	public Alergeno modificar(Alergeno alergeno) {
		try {
			if (alergenoRepo.existsById(alergeno.getId())) {
				return alergenoRepo.save(alergeno);
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
			if (alergenoRepo.existsById(id)) {
				alergenoRepo.deleteById(id);
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
			return alergenoRepo.existsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer contarRegistros() {
		try {
			return (int) alergenoRepo.count();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void resetearAutoIncrement() {
		try {
			alergenoRepo.resetearAutoInc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertarConId(Alergeno alergeno) {
		try {
			alergenoRepo.insertarConId(alergeno.getId(), alergeno.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
