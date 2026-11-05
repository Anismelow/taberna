package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.servicio.AlergenoServicio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.servicio.IvaServicio;
import com.ipartek.taberna.servicio.ProductoServicio;

import org.springframework.ui.Model;


@Controller 
public class MenuControlador {
	@Autowired
	private ProductoServicio productoServ;

	@Autowired
	private CategoriaServicio categoriaServ;

	@Autowired
	private IvaServicio ivaServ;
	
	@Autowired
	private AlergenoServicio alergenoServ;

@GetMapping("/menuProductos")
public String menuProductos(Model model) {
	model.addAttribute("listaProductos", productoServ.obtenerTodos());
	model.addAttribute("listaCategorias", categoriaServ.obtenerTodos());
	model.addAttribute("listaIvas", ivaServ.obtenerTodos());
	model.addAttribute("listaAlergenos", alergenoServ.obtenerTodos());
	model.addAttribute("objProducto", new Producto());
	return "productos";
}

	@GetMapping("/menuTpv")
	public String menuTpv() {
		return "tpv";
	}

	@GetMapping("/menuHistorico")
	public String menuHistorico() {
		return "historico";
	}

	@GetMapping("/menuCopia")
	public String menuCopia() {
		return "copia";
	}
}
