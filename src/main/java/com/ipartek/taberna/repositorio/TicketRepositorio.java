package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Ticket;

import jakarta.transaction.Transactional;

@Repository 
public interface TicketRepositorio extends JpaRepository <Ticket , Integer> {

    @Modifying
@Transactional 
@Query(value = "INSERT INTO tickets (id, fecha, hora) VALUES (:id, :fecha, :hora)", nativeQuery = true)
void insertarConId(@Param("id") Integer id, @Param("fecha") java.time.LocalDate fecha,
                   @Param("hora") java.time.LocalTime hora);
}
