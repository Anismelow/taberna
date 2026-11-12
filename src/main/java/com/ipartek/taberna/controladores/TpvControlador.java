package com.ipartek.taberna.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.taberna.modelo.LineaTicket;
import com.ipartek.taberna.modelo.Producto;
import com.ipartek.taberna.modelo.Ticket;
import com.ipartek.taberna.servicio.ProductoServicio;
import com.ipartek.taberna.servicio.TicketServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class TpvControlador {

    @Autowired
    private ProductoServicio productoServ;

    @Autowired
    private TicketServicio ticketServ;

    @PostMapping("/tpvAnadir")
    public String tpvAnadir(HttpSession sesion,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad) {

        Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

        if (carrito == null) {
            carrito = new LinkedHashMap<>();
        }

        // Si el producto ya está en el carrito, se suma la cantidad en lugar de duplicar la línea
        if (carrito.containsKey(productoId)) {
            LineaTicket existente = carrito.get(productoId);
            existente.setCantidad(existente.getCantidad() + cantidad);
        } else {
            Producto producto = productoServ.obtenerPorId(productoId);
            LineaTicket linea = new LineaTicket();
            linea.setProductoFK(producto);
            linea.setCantidad(cantidad);
            carrito.put(productoId, linea);
        }

        sesion.setAttribute("carrito", carrito);
        return "redirect:/menuTpv";
    }

    @GetMapping("/tpvQuitar")
    public String tpvQuitar(@RequestParam Integer productoId, HttpSession sesion) {
        Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

        if (carrito != null) {
            carrito.remove(productoId);
            sesion.setAttribute("carrito", carrito);
        }
        return "redirect:/menuTpv";
    }

    @PostMapping("/tpvGuardar")
    public String tpvGuardar(HttpSession sesion, RedirectAttributes redirect) {

        Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            redirect.addFlashAttribute("f_mensaje", "El ticket está vacío");
            return "redirect:/menuTpv";
        }

        Ticket ticket = new Ticket();
        ticket.setFecha(LocalDate.now());
        ticket.setHora(LocalTime.now());

        // Cada línea debe apuntar a su ticket antes de guardar, o la FK quedaría a null
        for (LineaTicket linea : carrito.values()) {
            linea.setTicketFK(ticket);
        }

        // El cascade (CascadeType.ALL) persiste las líneas junto con el ticket
        ticket.setListaLineas(new ArrayList<>(carrito.values()));
        Ticket resultado = ticketServ.insertar(ticket);

        if (resultado != null && resultado.getId() != null) {
            sesion.removeAttribute("carrito");
            redirect.addFlashAttribute("f_mensaje", "Ticket nº " + resultado.getId() + " guardado correctamente");
        } else {
            redirect.addFlashAttribute("f_mensaje", "No se pudo guardar el ticket");
        }

        return "redirect:/menuTpv";
    }

    @GetMapping("/tpvCancelar")
    public String tpvCancelar(HttpSession sesion) {
        sesion.removeAttribute("carrito");
        return "redirect:/menuTpv";
    }

}
