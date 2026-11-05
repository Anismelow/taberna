package com.ipartek.taberna.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class MenuControlador {

  @GetMapping("/menuProductos")
	public String menuProductos() {
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
