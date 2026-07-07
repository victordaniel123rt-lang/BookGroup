package com.BookGroup.BookGroup.integration;


import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.EmpleadoRepository;
import com.BookGroup.BookGroup.repository.SalaRepository;
import com.BookGroup.BookGroup.service.interfaces.EmpleadoService;
import static org.junit.jupiter.api.Assertions.*;
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
public class EmpleadoServiceTest {

    @Autowired
    private EmpleadoRepository repository;
    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private EmpleadoService service;

    @Test
    @Transactional
    void crear(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",reservas);
        EmpleadoDTO dto = Mapper.toEmpleadoDTO(empleado);
        EmpleadoDTO creado = this.service.crear(dto);
        assertNotNull(creado);
        assertEquals("Victor", creado.getNombre());
        assertTrue(creado.getId()>0);
        assertEquals("tecnologia", creado.getDepartamento());
    }

    @Test
    @Transactional
    void testBuscarPorid(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",reservas);
        Empleado guardado = this.repository.save(empleado);
        Long id = guardado.getId();
        EmpleadoDTO dto = this.service.buscarPorId(id);
        assertNotNull(dto);
        assertEquals("Victor",dto.getNombre());
    }

    @Test
    void testBuscarPorId_NotFound(){
        RuntimeException exception = assertThrows(RuntimeException.class, ()->this.service.buscarPorId(1L));
        assertEquals("No se encontro el empleado con el id : " + 1L, exception.getMessage());
    }

    @Test
    @Transactional
    void testBuscarTodos(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",reservas);
        Empleado empleado2 = new Empleado(null,"Victor Daniel","victor@example.com","tecnologias",reservas);
        Empleado guardado = this.repository.save(empleado);
        Empleado guardado2 = this.repository.save(empleado2);
        List<EmpleadoDTO> dtos = this.service.buscarTodos();
        assertNotNull(dtos);
        assertEquals(2,dtos.size());
    }

    @Test
    @Transactional
    void testActualizar(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",reservas);
        EmpleadoDTO empleadoP = new EmpleadoDTO(null,"Víctor","vdg@example.com","tecnologias segundo piso",new ArrayList<>());
        Empleado guardado = this.repository.save(empleado);
        Long id = guardado.getId();
        EmpleadoDTO actualizado = this.service.actualizar(id, empleadoP);
        assertNotNull(actualizado);
        assertEquals("Víctor", actualizado.getNombre());
        assertEquals("vdg@example.com", actualizado.getCorreo());
        assertEquals("tecnologias segundo piso", actualizado.getDepartamento());
    }


    @Test
    void testActualizar_NotFounded(){
        EmpleadoDTO empleadoP = new EmpleadoDTO(null,"Víctor","vdg@example.com","tecnologias segundo piso",new ArrayList<>());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.actualizar(5L, empleadoP));
        assertEquals("No se encontro el empleado con el id : " + 5L, exception.getMessage());
    }

    @Test
    @Transactional
    void testEliminar(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(null,"Victor","victor@example.com","tecnologia",reservas);
        Empleado guardado = this.repository.save(empleado);
        Long id = guardado.getId();
        EmpleadoDTO eliminado = this.service.eliminar(id);
        assertNotNull(eliminado);
    }

    @Test
    void testEliminar_NotFounded(){
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.eliminar(1L));
        assertEquals("No se encontro el empleado con el id : " + 1L, exception.getMessage());
    }



    @Test
    @Transactional
    void testEliminar_Exception(){
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
        lista.add(uno);
        lista.add(dos);
        Empleado actualizado = this.repository.save(guardado);
        Long id = actualizado.getId();
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.eliminar(id));
        assertEquals("No se puede eliminar el empleado pues tiene reservas activas", exception.getMessage());

    }


}
