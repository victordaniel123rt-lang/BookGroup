package com.BookGroup.BookGroup.dto;

import com.BookGroup.BookGroup.entity.Reserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa una sala de la empresa")
public class SalaDTO {
    @Schema(description = "ID de la sala, este autogenerado", example = "5", accessMode =Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre registrado para la sala", example = "BLUE", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;
    @Schema(description = "Capacidad de afore que tiene la sala", example = "1500", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer capacidad;
    @Schema(description = "Ubicacion de la sala", example = "Santa Ursula 4", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ubicacion;
    @Schema(description = "Valor de tipo booleano que indica si la sala cuenta con proyector visual", example = "true")
    private Boolean tieneProyector;
    @Schema(description = "Lista de reservaciones inscrita a esa sala hasta el momento de la consulta")
    private List<ReservaDTO> reservas;
}
