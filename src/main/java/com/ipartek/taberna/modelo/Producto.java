package com.ipartek.taberna.modelo;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 145, nullable = false)
    private String nombre;

    @Column(name = "precio", precision = 5, scale = 2, nullable = false)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn (name = "categoriaFK", nullable = false )
    private Categoria categoriaFK;


    @ManyToOne
    @JoinColumn (name = "ivaFK", nullable = false )
    private Iva ivaFK;

    @ManyToMany
@JoinTable(
    name = "producto_alergenos",
    joinColumns = @JoinColumn(name = "productoFK"),
    inverseJoinColumns = @JoinColumn(name = "alergenoFK")
)
private List<Alergeno> listaAlergenos;


}
