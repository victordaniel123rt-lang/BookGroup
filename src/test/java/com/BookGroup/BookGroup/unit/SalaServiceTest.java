package com.BookGroup.BookGroup.unit;


import com.BookGroup.BookGroup.dto.ReservaDTO;
import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.SalaRepository;
import com.BookGroup.BookGroup.service.impl.SalaServiceImpl;

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
public class SalaServiceTest {

    @Mock
    private SalaRepository repository;
    @InjectMocks
    private SalaServiceImpl service;

    @Test
    void testFindSalaById_SalaFound(){
        List<Reserva> reservas = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        when(this.repository.findById(1L)).thenReturn(Optional.of(sala));
        SalaDTO salaDB = this.service.buscarPorId(1L);
        assertNotNull(salaDB);
        assertEquals("AQUA", salaDB.getNombre());
        assertEquals(1500, salaDB.getCapacidad());
        verify(this.repository,times(1)).findById(1L);
    }


    @Test
    void testFindSalaById_SalaNotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->this.service.buscarPorId(1L));
        assertEquals("No se encontro la sala con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testFindAll(){
        List<ReservaDTO> reservas = new ArrayList<>();
        List<SalaDTO> salas = List.of(
                new SalaDTO(1L,"AQUA",1500,"Santa Ursula",true,reservas),
                new SalaDTO(2L,"AQUA 2",1200,"Santa Ursula",true,reservas)
        );
        List<Sala> entidades = salas.stream().map(Mapper::toSala).toList();
        when(this.repository.findAll()).thenReturn(entidades);
        List<SalaDTO> dtos = this.service.buscarTodos();
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        verify(this.repository,times(1)).findAll();

    }

    @Test
    void testCrear(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservas2 = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        SalaDTO creado = new SalaDTO(null,"AQUA",1500,"Santa Ursula",true,reservas2);
        when(this.repository.save(any(Sala.class))).thenReturn(sala);
        SalaDTO dto = this.service.crear(creado);
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertTrue(dto.getId()>0);
        assertEquals("AQUA", dto.getNombre());
        verify(this.repository,times(1)).save(any(Sala.class));
    }

    @Test
    void testActualizar_update(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservas2 = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        SalaDTO creado = new SalaDTO(null,"AQUA 2",1600,"Santa Ursula 4",true,reservas2);
        Sala actualizado = Mapper.toSala(creado);
        when(this.repository.findById(1L)).thenReturn(Optional.of(sala));
        when(this.repository.save(any(Sala.class))).thenReturn(actualizado);
        SalaDTO resultado = this.service.actualizar(1L, creado);
        assertNotNull(resultado);
        assertEquals("AQUA 2", resultado.getNombre());
        assertEquals(1600, resultado.getCapacidad());
        assertEquals("Santa Ursula 4", resultado.getUbicacion());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(any(Sala.class));
    }

    @Test
    void testActualizar_UpdateNotFound(){
        List<ReservaDTO> reservas2 = new ArrayList<>();
        SalaDTO creado = new SalaDTO(null,"AQUA 2",1600,"Santa Ursula 4",true,reservas2);
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> this.service.actualizar(1L,creado));
        assertEquals("No se encontro la sala con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);

    }

    @Test
    void testEliminar_Delete(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservas2 = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        SalaDTO creado = new SalaDTO(null,"AQUA 2",1600,"Santa Ursula 4",true,reservas2);
        when(this.repository.findById(1L)).thenReturn(Optional.of(sala));
        SalaDTO eliminado = this.service.eliminar(1L);
        assertNotNull(eliminado);
        verify(this.repository, times(1)).findById(1L);
        verify(this.repository,times(1)).delete(any(Sala.class));

    }


    @Test
    void testEliminar_NotFound(){
        when(this.repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()->this.service.eliminar(1L));
        assertEquals("No se encontro la sala con el id : " + 1L, exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void testEliminar_Exception(){
        List<Reserva> reservas = new ArrayList<>();
        List<ReservaDTO> reservas2 = new ArrayList<>();
        Sala sala = new Sala(1L,"AQUA",1500,"Santa Ursula",true,reservas);
        SalaDTO creado = new SalaDTO(null,"AQUA 2",1600,"Santa Ursula 4",true,reservas2);
        when(this.repository.findById(1L)).thenReturn(Optional.of(sala));
        when(this.repository.tieneReservacionesVigentes(1L, Estado.VIGENTE)).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, ()->this.service.eliminar(1L));
        assertEquals("No se puede eliminar la sala pues tiene reservaciones en estado vigente", exception.getMessage());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).tieneReservacionesVigentes(1L, Estado.VIGENTE);

    }





}
