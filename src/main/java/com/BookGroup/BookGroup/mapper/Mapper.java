package com.BookGroup.BookGroup.mapper;


import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;

import java.util.List;

public class Mapper {
    public static SalaDTO toSalaDTO(Sala entity){
        if(entity==null) return null;
        List<ReservaDTO> reservas = entity.getReservas().stream().map(Mapper::toReservaDTO).toList();
         return SalaDTO.builder()
                 .id(entity.getId())
                 .nombre(entity.getNombre())
                 .capacidad(entity.getCapacidad())
                 .ubicacion(entity.getUbicacion())
                 .tieneProyector(entity.getTieneProyector())
                 .reservas(reservas)
                 .build();
    }

    public static ReservaDTO toReservaDTO(Reserva entity){
        if(entity==null) return null;
        return ReservaDTO.builder()
                .id(entity.getId())
                .horaInicio(entity.getHoraInicio())
                .horaFin(entity.getHoraFin())
                .fecha(entity.getFecha())
                .empleado(entity.getEmpleado().getId())
                .sala(entity.getSala().getId())
                .build();
    }

    public static EmpleadoDTO toEmpleadoDTO(Empleado entity){
        if(entity==null) return null;
        List<ReservaDTO> reservas = entity.getReservas().stream().map(Mapper::toReservaDTO).toList();
       return EmpleadoDTO.builder()
                .nombre(entity.getNombre())
                .correo(entity.getCorreo())
                .departamento(entity.getDepartamento())
                .reservas(reservas)
                .build();
    }

    public static Sala toSala(SalaDTO dto){
        if(dto == null) return null;
        List<Reserva> reservas = dto.getReservas().stream().map(Mapper::toReserva).toList();
        return Sala.builder()
                .nombre(dto.getNombre())
                .ubicacion(dto.getUbicacion())
                .capacidad(dto.getCapacidad())
                .tieneProyector(dto.getTieneProyector())
                .reservas(reservas)
                .build();
    }

    public static Reserva toReserva(ReservaDTO dto){
        if(dto == null) return null;
        return Reserva.builder()
                .id(dto.getId())
                .empleado(Empleado.builder().id(dto.getEmpleado()).build())
                .sala(Sala.builder().id(dto.getSala()).build())
                .fecha(dto.getFecha())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .build();
    }

    public static Empleado toEmpleado(EmpleadoDTO dto){
        if(dto==null) return null;
        List<Reserva> reservas = dto.getReservas().stream().map(Mapper::toReserva).toList();
        return Empleado.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .departamento(dto.getDepartamento())
                .reservas(reservas)
                .build();
    }


    public static void updateSala(SalaDTO dto, Sala entity){
        if(dto == null || entity == null) return;
        entity.setNombre(dto.getNombre());
        entity.setCapacidad(dto.getCapacidad());
        entity.setUbicacion(dto.getUbicacion());
    }


    public static void uodateEmpleado(EmpleadoDTO dto, Empleado entity){
        if(dto == null || entity == null) return;
        entity.setNombre(dto.getNombre());
        entity.setCorreo(dto.getCorreo());
        entity.setDepartamento(dto.getDepartamento());

    }







}
