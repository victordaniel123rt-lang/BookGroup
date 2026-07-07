package com.BookGroup.BookGroup.unit;


import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.repository.ReservaRepository;
import com.BookGroup.BookGroup.service.impl.ReservaServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    @Mock
    private ReservaRepository repository;
    @InjectMocks
    private ReservaServiceImpl service;
    @Test
    void testBuscarTodos(){
        Sala sala = new Sala();
        Sala sala2 = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = List.of(
                new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado),
                new Reserva(2L,fecha,inicio,fin, Estado.VIGENTE,sala2,empleado)
        );
        when(this.repository.findAll()).thenReturn(lista);
        List<ReservaDTO> dtos = this.service.buscarTodos();
        assertEquals(2,dtos.size());
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Found(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        when(this.repository.findById(1L)).thenReturn(Optional.of(reserva));
        ReservaDTO dto = this.service.buscarPorId(1L);
        assertNotNull(dto);
        assertEquals(inicio, dto.getHoraInicio());
        assertEquals(fin,dto.getHoraFin());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.buscarPorId(1L));
        assertEquals("No se encontro la reservación con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testCrear(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        ReservaDTO reserva2 = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,sala.getId(),empleado.getId());
        when(this.repository.save(any(Reserva.class))).thenReturn(reserva);
        ReservaDTO creado = this.service.crear(reserva2);
        assertNotNull(creado);
        assertTrue(creado.getId()>0);
        assertEquals(inicio,creado.getHoraInicio());
        assertEquals(fin,creado.getHoraFin());
        verify(this.repository,times(1)).save(any(Reserva.class));

    }


    @Test
    void testCrear_FirstException(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 13:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.VIGENTE,sala.getId(),empleado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(reserva2));
        assertEquals("La hora de inicio no puede ser mayor a la hora final", exception.getMessage());

    }

    @Test
    void testCrear_SecondException(){
        List<Reserva> reservas = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.VIGENTE,sala.getId(),empleado.getId());
        when(this.repository.existeTraslape(sala.getId(), inicio, fin, Estado.VIGENTE)).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(reserva2));
        assertEquals("La sala ya se encuentra reservada en ese horario", exception.getMessage());
        verify(this.repository,times(1)).existeTraslape(1L, inicio,fin, Estado.VIGENTE);
    }

    @Test
    void testCrear_ThirdException(){
        List<Reserva> reservas = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-05",formato);
        String horaInicio = "2026-07-05 10:30:00";
        String horaFin = "2026-07-05 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.VIGENTE,sala.getId(),empleado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(reserva2));
        assertEquals("No se pueden hacer reservaciones en fechas pasadas", exception.getMessage());
    }
    @Test
    void testCrear_FourException(){
        List<Reserva> reservas = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("07-07-26",formato);
        String horaInicio = "2026-07-07 08:30:00";
        String horaFin = "2026-07-07 10:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.VIGENTE,sala.getId(),empleado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(reserva2));
        assertEquals("No se pueden hacer reservaciones en horarios pasados", exception.getMessage());
    }


    @Test
    void testActualizar_Found(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        Reserva reservaT = new Reserva(1L,fecha,inicio,fin, Estado.FINALIZADA,sala,empleado);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.FINALIZADA,sala.getId(),empleado.getId());
        when(this.repository.findById(1L)).thenReturn(Optional.of(reserva));
        when(this.repository.save(any(Reserva.class))).thenReturn(reservaT);
        ReservaDTO actualizado = this.service.actualizar(1L, reserva2);
        assertNotNull(actualizado);
        assertEquals(Estado.FINALIZADA, actualizado.getEstado());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(any(Reserva.class));
    }

    @Test
    void testActualizar_NotFound(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        ReservaDTO reserva2 = new ReservaDTO(1L,fecha,inicio,fin, Estado.FINALIZADA,sala.getId(),empleado.getId());
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.actualizar(1L,reserva2));
        assertEquals("No se encontro la reservación con el id : " + 1L, exception.getMessage());

    }


    @Test
    void testEliminar_Found(){
        Sala sala = new Sala();
        Empleado empleado = new Empleado();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        Reserva reserva = new Reserva(1L,fecha,inicio,fin, Estado.VIGENTE,sala,empleado);
        when(this.repository.findById(1L)).thenReturn(Optional.of(reserva));
        ReservaDTO eliminar = this.service.eliminar(1L);
        assertNotNull(eliminar);
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).delete(any(Reserva.class));
    }


    @Test
    void testEliminar_NotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.eliminar(1L));
        assertEquals("No se encontro la reservación con el id : " + 1L, exception.getMessage());
    }

}
