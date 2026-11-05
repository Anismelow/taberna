package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.servicio.AlergenoServicio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.servicio.IvaServicio;
import com.ipartek.taberna.servicio.ProductoServicio;


import org.springframework.ui.Model;


@Controller
public class ProductoControlador {

	@Autowired
	private ProductoServicio productoServ;

	@Autowired 
	private AlergenoServicio alergenoServ;

	@Autowired 
	private CategoriaServicio categoriaServ;

	@Autowired 
	private IvaServicio ivaServ;


	@PostMapping("/insertarProducto")
	public String insertarProducto(RedirectAttributes redirect, @ModelAttribute Producto objProducto) {
		try {
			Producto resultado = productoServ.insertar(objProducto);
			if (resultado != null && resultado.getId() != null) {
				redirect.addFlashAttribute("f_mensaje", "Producto insertado correctamente");
			} else {
				redirect.addFlashAttribute("f_mensaje", "No se pudo insertar el producto");
			}
		} catch (Exception e) {
			redirect.addFlashAttribute("f_mensaje", "Error");
		}
		return "redirect:/menuProductos";
	}

	@GetMapping("/borrarProducto")
	public String borrarProducto(RedirectAttributes redirect, @RequestParam Integer id) {
		try {
			Boolean resultado = productoServ.borrar(id);
			if (Boolean.TRUE.equals(resultado)) {
				redirect.addFlashAttribute("f_mensaje", "Producto borrado correctamente");
			} else {
				redirect.addFlashAttribute("f_mensaje", "No se pudo borrar el producto");
			}
		} catch (Exception e) {
			redirect.addFlashAttribute("f_mensaje", "Error");
		}
		return "redirect:/menuProductos";
	}

	@GetMapping("/modificarProducto")
public String modificarProducto(Model model, RedirectAttributes redirect, @RequestParam Integer id) {
	try {
		if (Boolean.TRUE.equals(productoServ.comprobarSiExiste(id))) {
			model.addAttribute("objProducto", productoServ.obtenerPorId(id));
			model.addAttribute("listaCategorias", categoriaServ.obtenerTodos());
			model.addAttribute("listaIvas", ivaServ.obtenerTodos());
			model.addAttribute("listaAlergenos", alergenoServ.obtenerTodos());
			return "frm_modificar_producto";
		} else {
			redirect.addFlashAttribute("f_mensaje", "El producto no existe");
			return "redirect:/menuProductos";
		}
	} catch (Exception e) {
		redirect.addFlashAttribute("f_mensaje", "Error en la BD");
		return "redirect:/menuProductos";
	}
}

@PostMapping("/modificarProducto")
public String modificarProducto(RedirectAttributes redirect, @ModelAttribute Producto objProducto) {
	try {
		Producto resultado = productoServ.modificar(objProducto);
		if (resultado != null && resultado.getId() != null) {
			redirect.addFlashAttribute("f_mensaje", "Producto modificado correctamente");
		} else {
			redirect.addFlashAttribute("f_mensaje", "No se pudo modificar el producto");
		}
	} catch (Exception e) {
		redirect.addFlashAttribute("f_mensaje", "Error");
	}
	return "redirect:/menuProductos";
}

}
