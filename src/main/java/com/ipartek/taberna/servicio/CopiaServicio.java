package com.ipartek.taberna.servicio;

import org.springframework.web.multipart.MultipartFile;

public interface CopiaServicio {

	String exportarCategorias();

    String exportarIvas();

    String exportarAlergenos();

    String exportarProductos();

    String exportarTodo();

    String exportarTickets();

    String exportarLineasTickets();

    String exportarProductosAlergenos();

    byte[] generarZip();

    void importarZip(MultipartFile archivoZip);
    


}
