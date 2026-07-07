package com.BookGroup.BookGroup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sala")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    private Boolean tieneProyector;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
}

