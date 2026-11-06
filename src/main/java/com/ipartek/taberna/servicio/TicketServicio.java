package com.ipartek.taberna.servicio;

import java.util.List;

import com.ipartek.taberna.modelo.Ticket;


public interface TicketServicio {

     public List<Ticket> obtenerTodos();

    public Ticket obtenerPorId(Integer id);

    public Ticket insertar(Ticket ticket);

    public Boolean borrar(Integer id);

    public Boolean comprobarSiExiste(Integer id);


}
