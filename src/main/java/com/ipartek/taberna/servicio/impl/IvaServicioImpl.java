package com.ipartek.taberna.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.taberna.modelo.Iva;
import com.ipartek.taberna.repositorio.IvaRepositorio;
import com.ipartek.taberna.servicio.IvaServicio;

@Service 
public class IvaServicioImpl implements IvaServicio {

    @Autowired
	private IvaRepositorio ivaRepo;

	@Override
	public List<Iva> obtenerTodos() {
		try {
			return ivaRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Iva obtenerPorId(Integer id) {
		try {
			return ivaRepo.findById(id).orElse(new Iva());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Iva insertar(Iva iva) {
		try {
			if (iva.getId() == null) {
				return ivaRepo.save(iva);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Iva modificar(Iva iva) {
		try {
			if (ivaRepo.existsById(iva.getId())) {
				return ivaRepo.save(iva);
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
			if (ivaRepo.existsById(id)) {
				ivaRepo.deleteById(id);
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
			return ivaRepo.existsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer contarRegistros() {
		try {
			return (int) ivaRepo.count();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void resetearAutoIncrement() {
		try {
			ivaRepo.resetearAutoInc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertarConId(Iva iva) {
		try {
			ivaRepo.insertarConId(iva.getId(), iva.getPorcentaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
