package com.BookGroup.BookGroup.controller;

import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.service.interfaces.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/empleados")
@RequiredArgsConstructor
@Tag(name = "empleados", description = "Controlador para la gestion y creacion de nuevos empleados")
public class EmpleadoController {
    private final EmpleadoService service;
    @GetMapping
    @Operation(summary = "listar todos los empleados", description = "Muestra todos los clientes que han sido dados de alta hasta el momento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista desplegada con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<List<EmpleadoDTO>> buscarTodos(){
        return ResponseEntity.ok(service.buscarTodos());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Encuentra a un empleado en especifico", description = "Muestra a un elemento en particular con identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elemento encontrado y mostrado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    @Operation(summary = "Crea un nuevo empleado", description = "Hace el registro de un nuevo empleado. No se debe enviar el Id en el cuerpo del JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<EmpleadoDTO> crear(@RequestBody EmpleadoDTO dto){
        return ResponseEntity.ok(service.crear(dto));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un empleado", description = "Hace la actualización correspondiente de un empleado. Es necesario incluir el id del empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Long id, @RequestBody EmpleadoDTO dto){
        return ResponseEntity.ok(service.actualizar(id,dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un empleado", description = "Elimina un empleado creado con anterioridad. Es necesario el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200/2004", description = "Elemento eliminado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o inconsistentes")
    })
    public ResponseEntity<EmpleadoDTO> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(service.eliminar(id));
    }
}
