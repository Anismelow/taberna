package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.LineaTicket;

import jakarta.transaction.Transactional;


@Repository 
public interface LineaTicketRepositorio extends  JpaRepository <LineaTicket , Integer>{

    @Modifying
@Transactional 
@Query(value = "INSERT INTO lineastickets (id, productoFK, cantidad, ticketFK) "
             + "VALUES (:id, :productoFK, :cantidad, :ticketFK)", nativeQuery = true)
void insertarConId(@Param("id") Integer id, @Param("productoFK") Integer productoFK,
                   @Param("cantidad") Integer cantidad, @Param("ticketFK") Integer ticketFK);
}
