package com.ipartek.taberna.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table (name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Entity 
public class Ticket {
     
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column (name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column (name = "hora", nullable = false)
    private LocalTime hora;

    @OneToMany(mappedBy = "ticketFK", cascade = CascadeType.ALL)
    private List<LineaTicket> listaLineas;

}
