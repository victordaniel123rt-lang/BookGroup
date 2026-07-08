package com.BookGroup.BookGroup.controller;

import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.service.interfaces.ReservaSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservas")
@RequiredArgsConstructor
@Tag(name = "reservas", description = "Controlador para la gestion y creacion de nuevas reservaciones")
public class ReservaController {
    private final ReservaSevice sevice;
    @GetMapping
    @Operation(summary = "listar todas las reservaciones", description = "Muestra todos las reservaciones que han sido dados de alta hasta el momento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista desplegada con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<List<ReservaDTO>> buscarTodos(){
        return ResponseEntity.ok(sevice.buscarTodos());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Encuentra a una reservacion en especifico", description = "Muestra a un elemento en particular con identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elemento encontrado y mostrado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(sevice.buscarPorId(id));
    }
    @PostMapping
    @Operation(summary = "Crea un nuevo reservación", description = "Hace el registro de una nueva reservación. No se debe enviar el Id en el cuerpo del JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<ReservaDTO> crear(@RequestBody ReservaDTO dto){
        return ResponseEntity.ok(sevice.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una reservación", description = "Hace la actualización correspondiente de una reservación. Es necesario incluir el id del empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<ReservaDTO> actualizar(@PathVariable Long id, @RequestBody ReservaDTO dto){
        return ResponseEntity.ok(sevice.actualizar(id,dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una reservación", description = "Elimina una reservacion creada con anterioridad. Es necesario el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200/2004", description = "Elemento eliminado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<ReservaDTO> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(sevice.eliminar(id));
    }

}
