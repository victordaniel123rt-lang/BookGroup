package com.BookGroup.BookGroup.service.impl;

import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.ReservaRepository;
import com.BookGroup.BookGroup.service.interfaces.ReservaSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.BookGroup.BookGroup.entity.Estado.VIGENTE;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaSevice {
    private final ReservaRepository repository;
    @Override
    public List<ReservaDTO> buscarTodos() {
        return repository.findAll().stream().map(Mapper::toReservaDTO).toList();
    }

    @Override
    public ReservaDTO buscarPorId(Long id) {
        Reserva reserva = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la reservación con el id : " + id)
        );
        return Mapper.toReservaDTO(reserva);
    }

    @Override
    public ReservaDTO crear(ReservaDTO dto) {
        LocalTime horaInicio = LocalTime.from(dto.getHoraInicio());
        LocalTime horaFin = LocalTime.from(dto.getHoraFin());
        boolean horario = horaInicio.isBefore(horaFin);
        if(!horario){
            throw  new IllegalArgumentException("La hora de inicio no puede ser mayor a la hora final");
        }
        boolean conflicto = repository.existeTraslape(dto.getSala(),dto.getHoraInicio(),dto.getHoraFin(),dto.getEstado());
        if(conflicto){
            throw new IllegalArgumentException("La sala ya se encuentra reservada en ese horario");
        }
        boolean antes = dto.getFecha().isBefore(LocalDate.now());
        if(antes){
            throw new IllegalArgumentException("No se pueden hacer reservaciones en fechas pasadas");
        }
        boolean horantes = dto.getHoraInicio().isBefore(LocalDateTime.now());
        if(horantes){
            throw new IllegalArgumentException("No se pueden hacer reservaciones en horarios pasados");
        }
        Reserva nuevo = Mapper.toReserva(dto);
        nuevo.setEstado(VIGENTE);
        Reserva guardado = repository.save(nuevo);
        return Mapper.toReservaDTO(guardado);
    }

    @Override
    public ReservaDTO actualizar(Long id, ReservaDTO dto) {
        Reserva reserva = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la reservación con el id : " + id)
        );
        reserva.setEstado(dto.getEstado());
        return Mapper.toReservaDTO(reserva);
    }

    @Override
    public ReservaDTO eliminar(Long id) {
        Reserva eliminar = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la reservación con el id : " + id)
        );
        repository.delete(eliminar);
        return Mapper.toReservaDTO(eliminar);
    }
}
