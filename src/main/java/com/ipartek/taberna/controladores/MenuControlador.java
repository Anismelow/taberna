package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.taberna.servicio.ProductoServicio;

import org.springframework.ui.Model;


@Controller 
public class MenuControlador {
	@Autowired
	private ProductoServicio productoServ;

  @GetMapping("/menuProductos")
	public String menuProductos(Model model) {
		model.addAttribute("listaProductos", productoServ.obtenerTodos());
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
