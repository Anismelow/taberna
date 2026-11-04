package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Iva;

import org.springframework.transaction.annotation.Transactional;

@Repository 
public interface IvaRepositorio extends JpaRepository <Iva, Integer> {

   @Modifying
    @Transactional
    @Query(value = "insert into ivas (id, porcentaje) values (:id, :porcentaje)", nativeQuery = true)
    public void insertarConId(@Param("id") Integer id, @Param("porcentaje") Integer porcentaje);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE ivas AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetearAutoInc();

}
