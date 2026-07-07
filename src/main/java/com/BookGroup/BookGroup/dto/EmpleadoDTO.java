package com.BookGroup.BookGroup.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String departamento;
    private List<ReservaDTO> reservas;
}
