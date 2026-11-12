package com.ipartek.taberna.servicio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.taberna.modelo.Categoria;
import com.ipartek.taberna.modelo.Iva;
import com.ipartek.taberna.modelo.Alergeno;
import com.ipartek.taberna.modelo.Producto;

import jakarta.annotation.PostConstruct;

@Service
public class CargarServicios {

	@Autowired
	private IvaServicio ivaServ;

	@Autowired
	private CategoriaServicio categoriaServ;

	@Autowired
	private AlergenoServicio alergenoServ;

	@Autowired
	private ProductoServicio productoServ;

	
	@PostConstruct
	public void cargarInicio() {
		cargarIvas();
		cargarCategorias();
		cargarAlergenos();
		cargarProductos();
	}


	private void cargarIvas() {
		if (ivaServ.contarRegistros() != null && ivaServ.contarRegistros() == 0) {
			ivaServ.resetearAutoIncrement();
			ivaServ.insertarConId(new Iva(1, 4));
			ivaServ.insertarConId(new Iva(2, 10));
			ivaServ.insertarConId(new Iva(3, 21));
		}
	}


	private void cargarCategorias() {
		if (categoriaServ.contarRegistros() != null && categoriaServ.contarRegistros() == 0) {
			categoriaServ.resetearAutoIncrement();
			categoriaServ.insertarConId(new Categoria(1, "Bocadillos"));
			categoriaServ.insertarConId(new Categoria(2, "Sandwiches"));
			categoriaServ.insertarConId(new Categoria(3, "Tortillas"));
			categoriaServ.insertarConId(new Categoria(4, "Refrescos"));
			categoriaServ.insertarConId(new Categoria(5, "Cafes"));
			categoriaServ.insertarConId(new Categoria(6, "Bebidas alcoholicas"));
		}
	}


	private void cargarAlergenos() {
		if (alergenoServ.contarRegistros() != null && alergenoServ.contarRegistros() == 0) {
			alergenoServ.resetearAutoIncrement();
			alergenoServ.insertarConId(new Alergeno(1, "Gluten"));
			alergenoServ.insertarConId(new Alergeno(2, "Leche"));
			alergenoServ.insertarConId(new Alergeno(3, "Soja"));
			alergenoServ.insertarConId(new Alergeno(4, "Marisco"));
			alergenoServ.insertarConId(new Alergeno(5, "Frutos secos"));
		}
	}

	// Los productos van los últimos porque dependen de categoría e IVA (claves foráneas)
	private void cargarProductos() {
		if (productoServ.contarRegistros() != null && productoServ.contarRegistros() == 0) {

			Producto p1 = new Producto();
			p1.setNombre("Bocadillo de tortilla");
			p1.setPrecio(new BigDecimal("3.50"));
			p1.setCategoriaFK(categoriaServ.obtenerPorId(1));   // Bocadillos
			p1.setIvaFK(ivaServ.obtenerPorId(2));               // 10%
			p1.setListaAlergenos(List.of(
					alergenoServ.obtenerPorId(1)));             // Gluten
			productoServ.insertar(p1);

			Producto p2 = new Producto();
			p2.setNombre("Cafe con leche");
			p2.setPrecio(new BigDecimal("1.40"));
			p2.setCategoriaFK(categoriaServ.obtenerPorId(5));   // Cafes
			p2.setIvaFK(ivaServ.obtenerPorId(2));               // 10%
			p2.setListaAlergenos(List.of(
					alergenoServ.obtenerPorId(2)));             // Leche
			productoServ.insertar(p2);
		}
	}
}