package com.BookGroup.BookGroup.dto;

import com.BookGroup.BookGroup.entity.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa una Reservacion validada por la empresa")
public class ReservaDTO {
    @Schema(description = "ID con el que se identifica la reservación y es autogenerado", example = "3", accessMode =Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Fecha en la cual quedo registrada la reservacion", example = "15-07-26", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;
    @Schema(description = "Hora de inicio del evento", example = "2026-07-15 10:30:00",  requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime horaInicio;
    @Schema(description = "Hora de fin del evento que quedo registrado", example = "2026-07-15 12:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime horaFin;
    @Schema(description = "Estado de la reservacion con valor tipo ENUM para valores: VIGENTE, CANCELADA, FINALIZADA",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Estado estado;
    @Schema(description = "Identificador unico de la sala en donde se realiza el evento", example = "4",requiredMode = Schema.RequiredMode.REQUIRED )
    private Long sala;
    @Schema(description = "Identificador único del empleado que realizo la reservacion", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long empleado;
}
