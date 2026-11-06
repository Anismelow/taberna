package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Ticket;

@Repository 
public interface TicketRepositorio extends JpaRepository <Ticket , Integer> {

}
