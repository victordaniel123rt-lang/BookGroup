package com.BookGroup.BookGroup.controller;


import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.service.interfaces.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/salas")
@RequiredArgsConstructor
public class SalaController {
    private final SalaService service;
    @GetMapping
    public ResponseEntity<List<SalaDTO>> buscarTodos(){
        return ResponseEntity.ok(service.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<SalaDTO> crear(@RequestBody SalaDTO dto){
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> actualizar(@RequestBody SalaDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(service.actualizar(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SalaDTO> eiminar(@PathVariable Long id){
        return ResponseEntity.ok(service.eliminar(id));
    }
}
