package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.LineaTicket;


@Repository 
public interface LineaTicketRepositorio extends  JpaRepository <LineaTicket , Integer>{

}
