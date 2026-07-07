package com.BookGroup.BookGroup.integration;

import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Reserva;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.SalaRepository;
import com.BookGroup.BookGroup.service.interfaces.SalaService;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class SalaServiceTest {

    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private SalaService service;

    @Test
    @Transactional
    void tes_Crear(){
    List<Reserva> reservas = new ArrayList<>();
    Sala sala = new Sala(null,"AQUA",1500,"Santa Ursula",true,reservas);
    SalaDTO test = Mapper.toSalaDTO(sala);
    SalaDTO creada = this.service.crear(test);
    assertNotNull(creada);
    assertNotNull(creada.getId());
    assertEquals("AQUA", creada.getNombre());
    }


    @Test
    @Transactional
    void testFindById(){
        Sala salaParaGuardar = new Sala(null, "AQUA", 1500, "Santa Ursula", true, new ArrayList<>());
        Sala salaGuardada = salaRepository.save(salaParaGuardar);
        Long idExistente = salaGuardada.getId();
        SalaDTO sala = this.service.buscarPorId(idExistente);
        assertNotNull(sala);
        assertEquals("AQUA", sala.getNombre());
    }

    @Test
    void testFindById_NotFound(){
        Long id = 5L;
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> this.service.buscarPorId(id));
        assertEquals("No se encontro la sala con el id : " + id, exception.getMessage());
    }


    @Test
    @Transactional
    void testFindAll(){
        Sala salaParaGuardar = new Sala(null, "AQUA", 1500, "Santa Ursula", true, new ArrayList<>());
        Sala salaParaGuardar2 = new Sala(null, "AQUA 2", 2000, "Santa Monica", true, new ArrayList<>());
        Sala guardado1 = salaRepository.save(salaParaGuardar);
        Sala guardado2 = salaRepository.save(salaParaGuardar2);
        List<SalaDTO> salas = this.service.buscarTodos();
        assertNotNull(salas);
        assertEquals(2,salas.size());
    }




}
