package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Alergeno;

import jakarta.transaction.Transactional;

@Repository 
public interface AlergenoRepositorio extends  JpaRepository <Alergeno, Integer>{
    @Modifying
    @Transactional 
    @Query(value = "insert into alergenos (id, nombre) values (:id, :nombre)", nativeQuery = true)
    public void insertarConId(@Param("id") Integer id, @Param("valor") Integer valor);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE alergenos AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetearAutoInc();

}
