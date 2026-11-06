package com.ipartek.taberna.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.taberna.modelo.Ticket;
import com.ipartek.taberna.repositorio.TicketRepositorio;
import com.ipartek.taberna.servicio.TicketServicio;

@Service
public class TicketServicioImpl implements TicketServicio {

    @Autowired
    private TicketRepositorio ticketRepo;

    @Override
    public List<Ticket> obtenerTodos() {
        try {
            return ticketRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Ticket obtenerPorId(Integer id) {
        try {
            return ticketRepo.findById(id).orElse(new Ticket());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket insertar(Ticket ticket){
        try{
            if(ticket.getId() == null){
             return ticketRepo.save(ticket);
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override 
    public Boolean borrar(Integer id){
        try{
            if(ticketRepo.existsById(id)){
                ticketRepo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

     @Override
	public Boolean comprobarSiExiste(Integer id) {
		try {
			return ticketRepo.existsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
