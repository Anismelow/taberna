package com.ipartek.taberna.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    ProductoServicio productoServ;

    @Autowired
private TicketServicio ticketServ;

    @PostMapping("/tpvAnadir")
    public String tpvAnadir(HttpSession sesion,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad) {

        // 1. sacar el carrito de la sesión
        Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

        // 2. si no existe todavía, crearlo
        if (carrito == null) {
            carrito = new LinkedHashMap<>();
        }

        // 3. agrupar: si ya está, sumo; si no, creo la línea
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

        // 4. guardar el carrito en la sesión
        sesion.setAttribute("carrito", carrito);

        // 5. redirigir al TPV
        return "redirect:/menuTpv";
    }

    @GetMapping("/tpvQuitar")
	public String tpvQuitar(
        @RequestParam Integer productoId,
        HttpSession sesion
    ){
        Map<Integer, LineaTicket> carrito = (Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

         if (carrito != null) {
            carrito.remove(productoId);
            sesion.setAttribute("carrito", carrito);
        }
        return "redirect:/menuTpv";

    }
    @PostMapping("/tpvGuardar")
public String tpvGuardar(HttpSession sesion, RedirectAttributes redirect) {

    // 1. sacar el carrito de la sesión (ya sabes hacerlo)
    Map<Integer, LineaTicket> carrito =(Map<Integer, LineaTicket>) sesion.getAttribute("carrito");

    // 2. si no hay carrito o está vacío, no se guarda nada
    if (carrito == null || carrito.isEmpty()) {
        redirect.addFlashAttribute("f_mensaje", "El ticket está vacío");
        return "redirect:/menuTpv";
    }

    // 3. crear el ticket con la fecha y la hora de AHORA
    Ticket ticket = new Ticket();
    ticket.setFecha(LocalDate.now());
    ticket.setHora(LocalTime.now());

    // 4. cada línea tiene que saber a qué ticket pertenece (¡la trampa!)
    for (LineaTicket linea : carrito.values()) {
        linea.setTicketFK(ticket);
    }

    // 5. pasar las líneas del mapa a una lista y dárselas al ticket
    ticket.setListaLineas(new ArrayList<>(carrito.values()));

    // 6. guardar el ticket (el cascade guarda las líneas solas)
    Ticket resultado = ticketServ.insertar(ticket);

    // 7. si se guardó bien: vaciar el carrito y avisar
    if (resultado != null && resultado.getId() != null) {
        sesion.removeAttribute("carrito");
        redirect.addFlashAttribute("f_mensaje", "Ticket nº " + resultado.getId() + " guardado correctamente");
    } else {
        redirect.addFlashAttribute("f_mensaje", "No se pudo guardar el ticket");
    }

    return "redirect:/menuTpv";
}
}
