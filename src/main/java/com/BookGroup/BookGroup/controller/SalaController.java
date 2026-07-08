package com.BookGroup.BookGroup.controller;


import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.service.interfaces.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/salas")
@RequiredArgsConstructor
@Tag(name = "salas", description = "Controlador para la gestion y creacion de nuevas salas")
public class SalaController {
    private final SalaService service;
    @GetMapping
    @Operation(summary = "listar todas las salas", description = "Muestra todos las salas que han sido dados de alta hasta el momento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista desplegada con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<List<SalaDTO>> buscarTodos(){
        return ResponseEntity.ok(service.buscarTodos());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Encuentra a una sala en especifico", description = "Muestra a un elemento en particular con identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elemento encontrado y mostrado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    @Operation(summary = "Crea una nueva sala", description = "Hace el registro de una nueva sala. No se debe enviar el Id en el cuerpo del JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<SalaDTO> crear(@RequestBody SalaDTO dto){
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una sala", description = "Hace la actualización correspondiente de una sala. Es necesario incluir el id del empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<SalaDTO> actualizar(@RequestBody SalaDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(service.actualizar(id,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una sala", description = "Elimina una sala creada con anterioridad. Es necesario el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200/2004", description = "Elemento eliminado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<SalaDTO> eiminar(@PathVariable Long id){
        return ResponseEntity.ok(service.eliminar(id));
    }
}
