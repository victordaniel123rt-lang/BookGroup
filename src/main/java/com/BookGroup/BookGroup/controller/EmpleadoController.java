package com.BookGroup.BookGroup.controller;

import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.service.interfaces.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService service;
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> buscarTodos(){
        return ResponseEntity.ok(service.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<EmpleadoDTO> crear(@RequestBody EmpleadoDTO dto){
        return ResponseEntity.ok(service.crear(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Long id, @RequestBody EmpleadoDTO dto){
        return ResponseEntity.ok(service.actualizar(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(service.eliminar(id));
    }
}
