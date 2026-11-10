package com.ipartek.taberna.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IvaServicio ivaServicio;

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
        StringBuilder sql = new StringBuilder();

        for (Categoria cat : categoriaServ.obtenerTodos()) {
            sql.append("INSERT INTO categorias (id, nombre) VALUES (")
                    .append(cat.getId())
                    .append(", '")
                    .append(cat.getNombre())
                    .append("');\n");
        }
        return sql.toString();
    }

    @Override
    public String exportarIvas() {
        StringBuilder sql = new StringBuilder();

        for (Iva iva : ivaServicio.obtenerTodos()) {
            sql.append("INSERT INTO ivas (id, porcentaje) VALUES (")
                    .append(iva.getId()).append(", ").append(iva.getPorcentaje()).append(");\n");
        }
        return sql.toString();
    }

    @Override
    public String exportarAlergenos() {
        StringBuilder sql = new StringBuilder();

        for (Alergeno alerg : alergenoServ.obtenerTodos()) {
            sql.append("INSERT INTO alergenos (id, nombre) VALUES (")
                    .append(alerg.getId())
                    .append(", '")
                    .append(alerg.getNombre())
                    .append("');\n");
        }
        return sql.toString();

    }

    @Override
    public String exportarProductos() {
        StringBuilder sql = new StringBuilder();
        for (Producto prod : productoServ.obtenerTodos()) {
            sql.append("INSERT INTO productos (id, nombre, precio, categoriaFK, ivaFK, foto) VALUES (")
                    .append(prod.getId())
                    .append(", '").append(prod.getNombre()).append("'")
                    .append(", ").append(prod.getPrecio())
                    .append(", ").append(prod.getCategoriaFK().getId())
                    .append(", ").append(prod.getIvaFK().getId())
                    .append(", '").append(prod.getFoto()).append("'")
                    .append(");\n");
        }
        return sql.toString();
    }

    @Override
    public String exportarTickets() {
        StringBuilder sql = new StringBuilder();
        for (Ticket tick : ticketServ.obtenerTodos()) {
            sql.append("INSERT INTO tickets (id, fecha, hora) VALUES (")
                    .append(tick.getId())
                    .append(", '").append(tick.getFecha()).append("'")
                    .append(", '").append(tick.getHora()).append("'")
                    .append(");\n");
        }
        return sql.toString();
    }   

  @Override
public String exportarLineasTickets() {
	StringBuilder sql = new StringBuilder();
	for (LineaTicket linea : lineaTicketRepo.findAll()) {   // 1 consulta, todas las líneas
		sql.append("INSERT INTO lineastickets (id, productoFK, cantidad, ticketFK) VALUES (")
		   .append(linea.getId())
		   .append(", ").append(linea.getProductoFK().getId())
		   .append(", ").append(linea.getCantidad())
		   .append(", ").append(linea.getTicketFK().getId())
		   .append(");\n");
	}
	return sql.toString();
}
    @Override
    public String exportarTodo() {
        StringBuilder sql = new StringBuilder();
        sql.append(exportarIvas());
        sql.append(exportarCategorias());
        sql.append(exportarAlergenos());
        sql.append(exportarProductos());
        sql.append(exportarTickets());
        sql.append(exportarLineasTickets());
        return sql.toString();
    }

}
