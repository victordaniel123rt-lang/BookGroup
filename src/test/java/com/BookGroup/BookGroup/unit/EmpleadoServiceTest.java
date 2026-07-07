package com.BookGroup.BookGroup.unit;


import com.BookGroup.BookGroup.dto.EmpleadoDTO;
import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.repository.EmpleadoRepository;
import com.BookGroup.BookGroup.service.impl.EmpleadoServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {
    @Mock
    private EmpleadoRepository repository;
    @InjectMocks
    private EmpleadoServiceImpl service;

    @Test
    void testBuscarPorId_Found(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas);
        when(this.repository.findById(1L)).thenReturn(Optional.of(empleado));
        EmpleadoDTO respuesta = this.service.buscarPorId(1L);
        assertNotNull(respuesta);
        assertEquals("Victor",respuesta.getNombre());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->this.service.buscarPorId(1L));
        assertEquals("No se encontro el empleado con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testbuscarTodos(){
        List<Reserva> reservas = new ArrayList<>();
        List<Empleado> lista = List.of(
                new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas),
                new Empleado(2L,"Daniel","daniel@example.com","tecnologia",reservas)

        );
        when(this.repository.findAll()).thenReturn(lista);
        List<EmpleadoDTO> empleadoDTOS = this.service.buscarTodos();
        assertEquals(2,empleadoDTOS.size());
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void testcrearEmpleado(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservasDTO = new ArrayList<>();
        Empleado empleado = new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas);
        EmpleadoDTO empleado2 = new EmpleadoDTO(null,"Victor","victor@example.com","tecnologia",reservasDTO);
        when(this.repository.save(any(Empleado.class))).thenReturn(empleado);
        EmpleadoDTO creado = this.service.crear(empleado2);
        assertNotNull(creado);
        assertEquals("Victor", creado.getNombre());
        assertTrue(creado.getId()>0);
        verify(this.repository,times(1)).save(any(Empleado.class));
    }

    @Test
    void testactualizar_updateFound(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservasDTO = new ArrayList<>();
        Empleado empleado = new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas);
        Empleado empleadoT = new Empleado(1L,"Victor Daniel","victor123@example.com","Asesoria",reservas);
        EmpleadoDTO empleado2 = new EmpleadoDTO(null,"Victor","victor@example.com","tecnologia",reservasDTO);
        when(this.repository.findById(1L)).thenReturn(Optional.of(empleado));
        when(this.repository.save(any(Empleado.class))).thenReturn(empleadoT);
        EmpleadoDTO actualizado = this.service.actualizar(1L, empleado2);
        assertNotNull(actualizado);
        assertEquals("Victor Daniel", actualizado.getNombre());
        assertEquals("Asesoria", actualizado.getDepartamento());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(any(Empleado.class));
    }

    @Test
    void testactualizar_updateNotFound(){
        List<ReservaDTO> reservasDTO = new ArrayList<>();
        EmpleadoDTO empleado2 = new EmpleadoDTO(null,"Victor","victor@example.com","tecnologia",reservasDTO);
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()->this.service.actualizar(1L, empleado2));
        assertEquals("No se encontro el empleado con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);

    }

    @Test
    void testEliminar_Found(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas);
        when(this.repository.findById(1L)).thenReturn(Optional.of(empleado));
        EmpleadoDTO eliminado = this.service.eliminar(1L);
        assertNotNull(eliminado);
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).delete(any(Empleado.class));
    }

    @Test
    void testEliminar_NotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.eliminar(1L));
        assertEquals("No se encontro el empleado con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testEliminar_Exception(){
        List<Reserva> reservas = new ArrayList<>();
        Empleado empleado = new Empleado(1L,"Victor","victor@example.com","tecnologia",reservas);
        when(this.repository.findById(1L)).thenReturn(Optional.of(empleado));
        when(this.repository.tieneReservacionesVigentes(1L, Estado.VIGENTE)).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, ()->this.service.eliminar(1L));
        assertEquals("No se puede eliminar el empleado pues tiene reservas activas", exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).tieneReservacionesVigentes(1L, Estado.VIGENTE);
    }







}
