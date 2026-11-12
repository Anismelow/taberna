package com.ipartek.taberna.controladores;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	public String insertarProducto(RedirectAttributes redirect,
			@RequestParam("imagenProducto") MultipartFile archivoFoto,
			@ModelAttribute Producto objProducto) {
		try {

			if (archivoFoto != null && !archivoFoto.isEmpty()) {
				String nombreOriginal = archivoFoto.getOriginalFilename();
				String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal;
				objProducto.setFoto(nombreFinal);
				Path carpeta = Paths.get("uploads/image");
				Files.createDirectories(carpeta);
				Files.write(carpeta.resolve(nombreFinal), archivoFoto.getBytes());
			}

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
	public String modificarProducto(Model model, RedirectAttributes redirect, Integer id) {
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
	public String modificarProducto(RedirectAttributes redirect,
			@RequestParam(name = "imagenFoto", required = false) MultipartFile archivoFoto,
			@ModelAttribute Producto objProducto) {
		try {
			if (archivoFoto != null && !archivoFoto.isEmpty()) {
				// SÍ hay foto nueva: la guardo y pongo su nombre
				String nombreOriginal = archivoFoto.getOriginalFilename();
				String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal;
				objProducto.setFoto(nombreFinal);

				Path carpeta = Paths.get("uploads/image");
				Files.createDirectories(carpeta);
				Files.write(carpeta.resolve(nombreFinal), archivoFoto.getBytes());
			} else {
				// NO hay foto nueva: recupero la que ya tenía en la BD
				Producto productoViejo = productoServ.obtenerPorId(objProducto.getId());
				objProducto.setFoto(productoViejo.getFoto());
			}

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

	@GetMapping("/buscarProductos")
	public String buscarProducto(
			Model model,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "categoriaId", required = false) Integer categoriaId,
			@RequestParam(name = "precioMin", required = false) Double precioMin,
			@RequestParam(name = "precioMax", required = false) Double precioMax) {

		model.addAttribute("listaProductos", productoServ.buscar(nombre, categoriaId, precioMin, precioMax));
		model.addAttribute("listaCategorias", categoriaServ.obtenerTodos());
		model.addAttribute("listaIvas", ivaServ.obtenerTodos());
		model.addAttribute("listaAlergenos", alergenoServ.obtenerTodos());
		model.addAttribute("objProducto", new Producto());

		return "productos";
	}
}
