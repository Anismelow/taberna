package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.repositorio.ProductoRepositorio;
import com.ipartek.taberna.servicio.ProductoServicio;

@Controller
public class ProductoControlador {

    @Autowired 
    private ProductoServicio productoServ;

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


}
