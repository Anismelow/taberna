package com.ipartek.taberna.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.taberna.modelo.LineaTicket;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.repositorio.LineaTicketRepositorio;
import com.ipartek.taberna.repositorio.TicketRepositorio;
import com.ipartek.taberna.servicio.ProductoServicio;

import jakarta.servlet.http.HttpSession;

@Controller 
public class TpvControlador {

    @Autowired
    TicketRepositorio ticketrepo;

    @Autowired
    LineaTicketRepositorio lineaTicketRepo;

    @Autowired
    ProductoServicio productoServ;

    @PostMapping("/tpvAnadir")
    public String tpvAnadir(HttpSession sesion,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad) {

        // 1. sacar la lista de la sesión
        List<LineaTicket> carrito = (List<LineaTicket>) sesion.getAttribute("carrito");

        // 2. si no existe todavía, crearla → ¿cómo? piénsalo
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        // 3. crear la línea nueva (producto + cantidad)
        Producto producto = productoServ.obtenerPorId(productoId);
        LineaTicket linea = new LineaTicket();
        linea.setProductoFK(producto);
        linea.setCantidad(cantidad);

        // 4. añadir la línea al carrito
        carrito.add(linea);

        // 5. guardar el carrito en la sesión
        sesion.setAttribute("carrito", carrito);

        // 6. redirigir al TPV
        return "redirect:/menuTpv";
    }

}
