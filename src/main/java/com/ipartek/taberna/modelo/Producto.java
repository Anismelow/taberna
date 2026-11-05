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

    @Column (name = "foto", length = 255, nullable = true)
    private String foto;

    @ManyToMany
@JoinTable(
    name = "productos_alergenos",
    joinColumns = @JoinColumn(name = "productoFK"),
    inverseJoinColumns = @JoinColumn(name = "alergenoFK")
)
private List<Alergeno> listaAlergenos;


	public BigDecimal getPrecioConIva() {
		if (precio == null || ivaFK == null) {
			return precio;
		}
		BigDecimal factor = BigDecimal.valueOf(100 + ivaFK.getPorcentaje())
				.divide(BigDecimal.valueOf(100));
		return precio.multiply(factor).setScale(2, java.math.RoundingMode.HALF_UP);
	}

}
