package com.ipartek.taberna.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.taberna.modelo.Producto;


@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {

    @Query("""
            SELECT p FROM Producto p
            WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%)
              AND (:categoriaId IS NULL OR p.categoriaFK.id = :categoriaId)
              AND (:precioMin IS NULL OR p.precio >= :precioMin)
              AND (:precioMax IS NULL OR p.precio <= :precioMax)
            """)
    public List<Producto> buscar(@Param("nombre") String nombre,
            @Param("categoriaId") Integer categoriaId,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax);
}
