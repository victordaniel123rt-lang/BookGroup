package com.BookGroup.BookGroup.integration;
import static org.junit.jupiter.api.Assertions.*;

import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.repository.EmpleadoRepository;
import com.BookGroup.BookGroup.repository.ReservaRepository;
import com.BookGroup.BookGroup.repository.SalaRepository;
import com.BookGroup.BookGroup.service.interfaces.ReservaSevice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ReservaServiceTest {
    @Autowired
    private ReservaSevice service;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private EmpleadoRepository repository;

    @Test
    @Transactional
    void testCCrear(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        ReservaDTO uno = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),guardado.getId());
        ReservaDTO crear = this.service.crear(uno);
        assertNotNull(crear);
        assertEquals(fecha,crear.getFecha());
    }

    @Test
    @Transactional
    void testCrear_FirstException(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 13:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        ReservaDTO uno = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),guardado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(uno));
        assertEquals("La hora de inicio no puede ser mayor a la hora final", exception.getMessage());
    }

    @Test
    @Transactional
    void testCrear_SecondException(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        Reserva existente = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado1,guardado);
        Reserva exists = this.reservaRepository.save(existente);
        ReservaDTO uno = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),guardado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(uno));
        assertEquals("La sala ya se encuentra reservada en ese horario", exception.getMessage());
    }
    @Test
    @Transactional
    void testCrear_ThirdException(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("05-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        ReservaDTO uno = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),guardado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(uno));
        assertEquals("No se pueden hacer reservaciones en fechas pasadas", exception.getMessage());
    }
    @Test
    @Transactional
    void testCrear_LastException(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("07-07-26",formato);
        String horaInicio = "2026-07-07 10:30:00";
        String horaFin = "2026-07-07 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        ReservaDTO uno = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),guardado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.crear(uno));
        assertEquals("No se pueden hacer reservaciones en horarios pasados", exception.getMessage());
    }

    @Test
    @Transactional
    void testFindById(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        Reserva uno = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado1,empleado);
        Reserva guarda = this.reservaRepository.save(uno);
        Long id = guarda.getId();
        ReservaDTO encontrado = this.service.buscarPorId(id);
        assertNotNull(encontrado);
        assertEquals(fecha, encontrado.getFecha());
    }


    @Test
    void testBuscarPorId_NotFounded(){
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.buscarPorId(5L));
        assertEquals("No se encontro la reservación con el id : " + 5L, exception.getMessage());
    }

    @Test
    @Transactional
    void testActualizado_Updated(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        Reserva uno = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado1,empleado);
        Reserva guarda = this.reservaRepository.save(uno);
        Long id = guarda.getId();
        ReservaDTO dto = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),empleado.getId());
        ReservaDTO actualizado = this.service.actualizar(id, dto);
        assertNotNull(actualizado);
        assertEquals(fecha, actualizado.getFecha());
        assertEquals(inicio, actualizado.getHoraInicio());
    }


    @Test
    void testActualizar_NotFounded(){
        Empleado empleado = new Empleado();
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        ReservaDTO dto = new ReservaDTO(null,fecha,inicio,fin, Estado.VIGENTE,guardado1.getId(),empleado.getId());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.actualizar(1L,dto));
        assertEquals("No se encontro la reservación con el id : " + 1L, exception.getMessage());
    }


    @Test
    @Transactional
    void testBuscarTodos_FindAll(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        Reserva uno = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado1,empleado);
        Reserva dos = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado2,empleado);
        Reserva guarda1 = this.reservaRepository.save(uno);
        Reserva guarda2 = this.reservaRepository.save(dos);
        List<ReservaDTO> reservas = this.service.buscarTodos();
        assertNotNull(reservas);
        assertEquals(2, reservas.size());
    }


    @Test
    @Transactional
    void testEliminar(){
        Sala sala = new Sala();
        Sala guardado1 = this.salaRepository.save(sala);
        Sala sala2 = new Sala();
        Sala guardado2 = this.salaRepository.save(sala2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate fecha = LocalDate.parse("15-07-26",formato);
        String horaInicio = "2026-07-15 10:30:00";
        String horaFin = "2026-07-15 12:30:00";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(horaInicio, formateador);
        LocalDateTime fin = LocalDateTime.parse(horaFin, formateador);
        List<Reserva> lista = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",lista);
        Empleado guardado = this.repository.save(empleado);
        Reserva uno = new Reserva(null,fecha,inicio,fin, Estado.VIGENTE,guardado1,empleado);
        Reserva guarda = this.reservaRepository.save(uno);
        Long id = guarda.getId();
        ReservaDTO eliminado = this.service.eliminar(id);
        assertNotNull(eliminado);


    }






}
