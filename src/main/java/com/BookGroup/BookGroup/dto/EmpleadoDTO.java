package com.BookGroup.BookGroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa la descripción de un empleado")
public class EmpleadoDTO {
    @Schema(description = "ID único del empleado (autogenerado)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre con el que se identifica el empleado", example = "Víctor", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;
    @Schema(description = "Correo para contactar al empleado", example = "vdge@example.com")
    private String correo;
    @Schema(description = "Departamento al que esta asignado el empleado", example = "tecnologia")
    private String departamento;
    @Schema(description = "Indica las reservaciones que el empleado ha registrado")
    private List<ReservaDTO> reservas;
}
