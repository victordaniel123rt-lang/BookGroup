package com.BookGroup.BookGroup.service.interfaces;

import com.BookGroup.BookGroup.dto.ReservaDTO;

import java.util.List;

public interface ReservaSevice {
    List<ReservaDTO> buscarTodos();
    ReservaDTO buscarPorId(Long id);
    ReservaDTO crear(ReservaDTO dto);
    ReservaDTO actualizar(Long id, ReservaDTO dto);
    ReservaDTO eliminar(Long id);
}
