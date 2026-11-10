package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.taberna.servicio.AlergenoServicio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.servicio.CopiaServicio;
import com.ipartek.taberna.servicio.IvaServicio;
import com.ipartek.taberna.modelo.Categoria;
import com.ipartek.taberna.modelo.LineaTicket;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.modelo.Ticket;


@Controller 
public class CopiaControlador {

  @Autowired 
  private CopiaServicio copiaServ;

    @Autowired
    private CategoriaServicio categoriaServ;

    @Autowired
    private IvaServicio ivaServicio;

    @Autowired
    private AlergenoServicio alergenoServ;





    @GetMapping("/exportarCopia")
public ResponseEntity<String> exportarCopia() {

	String sql = copiaServ.exportarTodo();
  
	String nombreArchivo = "copia_taberna.sql";

	return ResponseEntity.ok()
			.header("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"")
			.contentType(MediaType.TEXT_PLAIN)
			.body(sql);
}

}
