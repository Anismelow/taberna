package com.ipartek.taberna.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.taberna.servicio.CopiaServicio;

@Controller
public class CopiaControlador {

  @Autowired
  private CopiaServicio copiaServ;

  @GetMapping("/exportarCopia")
  public ResponseEntity<byte[]> exportarCopia() {

    byte[] zip = copiaServ.generarZip();
    String nombreArchivo = "copia_taberna.zip";

    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"")
        .contentType(MediaType.parseMediaType("application/zip"))
        .body(zip);
  }

  @PostMapping("/importarCopia")
  public String importarCopia(@RequestParam("archivoZip") MultipartFile archivoZip,
      RedirectAttributes redirect) {
    copiaServ.importarZip(archivoZip);
    redirect.addFlashAttribute("f_mensaje", "Copia restaurada correctamente");
    return "redirect:/menuCopia";
  }
}