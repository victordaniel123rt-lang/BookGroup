package com.BookGroup.BookGroup.service.interfaces;

import com.BookGroup.BookGroup.dto.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoDTO> buscarTodos();
    EmpleadoDTO buscarPorId(Long id);
    EmpleadoDTO crear(EmpleadoDTO dto);
    EmpleadoDTO actualizar(Long id, EmpleadoDTO dto);
    EmpleadoDTO eliminar(Long id);
}
