package com.ipartek.taberna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Categoria;

import jakarta.transaction.Transactional;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into categorias (id, nombre) values (:id, :nombre)", nativeQuery = true)
    public void insertarConId(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE categorias AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetearAutoInc();

}
