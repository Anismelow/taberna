package com.ipartek.taberna.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.taberna.modelo.LineaTicket;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.servicio.AlergenoServicio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.servicio.IvaServicio;
import com.ipartek.taberna.servicio.ProductoServicio;

import jakarta.servlet.http.HttpSession;

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
public String menuTpv(Model model, HttpSession sesion) {
    model.addAttribute("listaProductos", productoServ.obtenerTodos());
    model.addAttribute("listaCategorias", categoriaServ.obtenerTodos());

    Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

    if (carrito == null) {
        carrito = new LinkedHashMap<>();
    }

    // ===== calcular el total (variable LOCAL, aquí dentro) =====
    BigDecimal total = BigDecimal.ZERO;

    for (LineaTicket linea : carrito.values()) {
        BigDecimal subtotal = linea.getProductoFK().getPrecioConIva()
                                  .multiply(BigDecimal.valueOf(linea.getCantidad()));
        total = total.add(subtotal);
    }

    model.addAttribute("carrito", carrito);
    model.addAttribute("totalTicket", total);   // ← ahora sí, al final

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
