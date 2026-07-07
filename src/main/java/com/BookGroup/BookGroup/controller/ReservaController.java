package com.BookGroup.BookGroup.controller;

import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.service.interfaces.ReservaSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaSevice sevice;
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> buscarTodos(){
        return ResponseEntity.ok(sevice.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(sevice.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<ReservaDTO> crear(@RequestBody ReservaDTO dto){
        return ResponseEntity.ok(sevice.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizar(@PathVariable Long id, @RequestBody ReservaDTO dto){
        return ResponseEntity.ok(sevice.actualizar(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaDTO> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(sevice.eliminar(id));
    }

}
