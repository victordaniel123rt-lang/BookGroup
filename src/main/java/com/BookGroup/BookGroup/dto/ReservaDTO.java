package com.BookGroup.BookGroup.dto;

import com.BookGroup.BookGroup.entity.Estado;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Estado estado;
    private Long sala;
    private Long empleado;
}
