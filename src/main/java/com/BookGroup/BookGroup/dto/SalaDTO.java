package com.BookGroup.BookGroup.dto;

import com.BookGroup.BookGroup.entity.Reserva;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaDTO {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    private Boolean tieneProyector;
    private List<ReservaDTO> reservas;
}
