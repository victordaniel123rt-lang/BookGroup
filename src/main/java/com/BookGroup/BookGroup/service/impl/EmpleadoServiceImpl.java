package com.BookGroup.BookGroup.service.impl;

import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.EmpleadoRepository;
import com.BookGroup.BookGroup.service.interfaces.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository repository;
    @Override
    public List<EmpleadoDTO> buscarTodos() {
        return repository.findAll().stream().map(Mapper::toEmpleadoDTO).toList();
    }

    @Override
    public EmpleadoDTO buscarPorId(Long id) {
        Empleado empleado = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro el empleado con el id : " + id)
        );
        return Mapper.toEmpleadoDTO(empleado);
    }

    @Override
    public EmpleadoDTO crear(EmpleadoDTO dto) {
        Empleado empleado = Mapper.toEmpleado(dto);
        Empleado guardado = repository.save(empleado);
        return Mapper.toEmpleadoDTO(guardado);
    }

    @Override
    public EmpleadoDTO actualizar(Long id, EmpleadoDTO dto) {
        Empleado empleado = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro el empleado con el id : " + id)
        );
        Mapper.uodateEmpleado(dto,empleado);
        Empleado actualizado = repository.save(empleado);
        return Mapper.toEmpleadoDTO(actualizado);
    }

    @Override
    public EmpleadoDTO eliminar(Long id) {
        Empleado eliminar = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro el empleado con el id : " + id)
        );
        boolean tieneReservasActivas = repository.tieneReservacionesVigentes(id,Estado.VIGENTE);
        if(tieneReservasActivas){
            throw new IllegalArgumentException("No se puede eliminar el empleado pues tiene reservas activas");
        }
        repository.delete(eliminar);
        return Mapper.toEmpleadoDTO(eliminar);
    }
}
