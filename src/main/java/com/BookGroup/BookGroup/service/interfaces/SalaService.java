package com.BookGroup.BookGroup.service.interfaces;

import com.BookGroup.BookGroup.dto.SalaDTO;

import java.util.List;

public interface SalaService {
    List<SalaDTO> buscarTodos();
    SalaDTO buscarPorId(Long id);
    SalaDTO crear(SalaDTO dot);
    SalaDTO actualizar(Long id, SalaDTO dto);
    SalaDTO eliminar(Long id);
}
