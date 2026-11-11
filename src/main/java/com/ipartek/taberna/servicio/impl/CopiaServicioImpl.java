package com.ipartek.taberna.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.taberna.modelo.Categoria;
import com.ipartek.taberna.servicio.AlergenoServicio;
import com.ipartek.taberna.servicio.CategoriaServicio;
import com.ipartek.taberna.servicio.CopiaServicio;
import com.ipartek.taberna.servicio.IvaServicio;
import com.ipartek.taberna.servicio.ProductoServicio;
import com.ipartek.taberna.servicio.TicketServicio;
import com.ipartek.taberna.modelo.Iva;
import com.ipartek.taberna.modelo.Alergeno;
import com.ipartek.taberna.modelo.LineaTicket;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.modelo.Ticket;
import com.ipartek.taberna.repositorio.LineaTicketRepositorio;

@Service
public class CopiaServicioImpl implements CopiaServicio {

    @Autowired
    private CategoriaServicio categoriaServ;

    @Autowired
    private IvaServicio ivaServ;

    @Autowired
    private AlergenoServicio alergenoServ;

    @Autowired
    private ProductoServicio productoServ;

    @Autowired
    private TicketServicio ticketServ;

    @Autowired
    private LineaTicketRepositorio lineaTicketRepo;

    @Override
    public String exportarCategorias() {
        StringBuilder csv = new StringBuilder();

        csv.append("id,nombre\n");
        for (Categoria cat : categoriaServ.obtenerTodos()) {
            csv.append(cat.getId()).append(",")
                    .append(cat.getNombre()).append("\n");
        }
        return csv.toString();
    }

    @Override
    public String exportarIvas() {
        StringBuilder csv = new StringBuilder();

        csv.append("id,porcentaje\n");
        for (Iva iva : ivaServ.obtenerTodos()) {
            csv.append(iva.getId()).append(",")
                    .append(iva.getPorcentaje()).append("\n");
        }
        return csv.toString();
    }

    @Override
    public String exportarAlergenos() {
        StringBuilder csv = new StringBuilder();
        csv.append("id,nombre\n");
        for (Alergeno alerg : alergenoServ.obtenerTodos()) {
            csv.append(alerg.getId()).append(",")
                    .append(alerg.getNombre()).append("\n");
        }
        return csv.toString();

    }

    @Override
    public String exportarProductos() {
        StringBuilder csv = new StringBuilder();
        csv.append("id,nombre,precio,categoriaFK,ivaFK,foto\n"); // cabecera

        for (Producto prod : productoServ.obtenerTodos()) {
            csv.append(prod.getId()).append(",")
                    .append(prod.getNombre()).append(",")
                    .append(prod.getPrecio()).append(",")
                    .append(prod.getCategoriaFK().getId()).append(",")
                    .append(prod.getIvaFK().getId()).append(",")
                    .append(prod.getFoto()).append("\n");
        }
        return csv.toString();
    }

    @Override
    public String exportarProductosAlergenos() {
        StringBuilder csv = new StringBuilder();
        csv.append("productoFK,alergenoFK");
        for (Producto prod : productoServ.obtenerTodos()) {
            csv.append("productoFK").append(",").append("\n");
            for (Alergeno ale : prod.getListaAlergenos()) {
                csv.append("alergenoFK").append(",").append("\n");
            }
        }
        return csv.toString();
    }

    @Override
    public String exportarTickets() {
        StringBuilder csv = new StringBuilder();
        csv.append("id,fecha,hora\n");
        for (Ticket ticket : ticketServ.obtenerTodos()) {
            csv.append(ticket.getId()).append(",")
                    .append(ticket.getFecha()).append(",")
                    .append(ticket.getHora()).append(",");
        }

        return csv.toString();
    }

    @Override
    public String exportarLineasTickets() {
        StringBuilder csv = new StringBuilder();
        for (LineaTicket linea : lineaTicketRepo.findAll()) {
            csv.append("id,productoFK,cantidad,ticketFK\n");
            csv.append(linea.getId());
            csv.append(", ").append(linea.getProductoFK().getId());
            csv.append(", ").append(linea.getCantidad());
            csv.append(", ").append(linea.getTicketFK().getId());
            csv.append("\n");
        }
        return csv.toString();
    }

    @Override
    public String exportarTodo() {
        StringBuilder csv = new StringBuilder();
        csv.append(exportarIvas());
        csv.append(exportarCategorias());
        csv.append(exportarAlergenos());
        csv.append(exportarProductos());
        csv.append(exportarProductosAlergenos());
        csv.append(exportarTickets());
        csv.append(exportarLineasTickets());
        return csv.toString();
    }

    private void importarCategorias(String contenido) {
	String[] lineas = contenido.split("\n");  
	for (int i = 1; i < lineas.length; i++) {  
		String[] partes = lineas[i].split(","); 
		if (partes.length < 2) continue;        
		Categoria cat = new Categoria();
		cat.setId(Integer.parseInt(partes[0].trim()));   
		cat.setNombre(partes[1].trim());
		categoriaServ.insertarConId(cat);           
	}
}

  private void importarIvas(String contenido) {
	String[] lineas = contenido.split("\n");  
	for (int i = 1; i < lineas.length; i++) {  
		String[] partes = lineas[i].split(","); 
		if (partes.length < 2) continue;        
		Iva iva = new Iva();
		iva.setId(Integer.parseInt(partes[0].trim()));   
		iva.setPorcentaje(Integer.parseInt(partes[1].trim()));
		ivaServ.insertarConId(iva);           
	}
}

  private void importarAlergenos(String contenido) {
	String[] lineas = contenido.split("\n");   
	for (int i = 1; i < lineas.length; i++) {  
		String[] partes = lineas[i].split(",");
		if (partes.length < 2) continue;        
		Alergeno alergeno = new Alergeno();
		alergeno.setId(Integer.parseInt(partes[0].trim())); 
		alergeno.setNombre(partes[1].trim());
		alergenoServ.insertarConId(alergeno);               
	}
}




    private void anadirAlZip(ZipOutputStream zip, String nombre, String contenido) throws Exception {
        zip.putNextEntry(new ZipEntry(nombre));
        zip.write(contenido.getBytes("UTF-8"));
        zip.closeEntry();
    }

    public byte[] generarZip() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(baos)) {
            File carpetaImagenes = new File("uploads/image");
            File[] imagenes = carpetaImagenes.listFiles();

            if (imagenes != null) {
                for (File img : imagenes) {
                    if (img.isFile()) {
                        zip.putNextEntry(new ZipEntry("imagenes/" + img.getName()));
                        zip.write(Files.readAllBytes(img.toPath()));
                        zip.closeEntry();
                    }
                }
            }

            anadirAlZip(zip, "ivas.csv", exportarIvas());
            anadirAlZip(zip, "categorias.csv", exportarCategorias());
            anadirAlZip(zip, "alergenos.csv", exportarAlergenos());
            anadirAlZip(zip, "productos.csv", exportarProductos());
            anadirAlZip(zip, "productos_alergenos.csv", exportarProductosAlergenos());
            anadirAlZip(zip, "tickets.csv", exportarTickets());
            anadirAlZip(zip, "lineastickets.csv", exportarLineasTickets());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    private String leerContenido(ZipInputStream zip) throws Exception {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buffer = new byte[1024];
	int leidos;
	while ((leidos = zip.read(buffer)) > 0) {
		baos.write(buffer, 0, leidos);
	}
	return baos.toString("UTF-8");
}

  public void importarZip(MultipartFile archivoZip) {
	try (ZipInputStream zip = new ZipInputStream(archivoZip.getInputStream())) {
		ZipEntry entrada;
		while ((entrada = zip.getNextEntry()) != null) {
			String nombre = entrada.getName();

			if (nombre.equals("categorias.csv")) {
				importarCategorias(leerContenido(zip));
			}
			else if (nombre.equals("ivas.csv")){
                    importarIvas(leerContenido(zip));
            }
            else if (nombre.equals("alergenos.csv")) {
                importarAlergenos(leerContenido(zip));
}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
