package com.BookGroup.BookGroup.service.impl;
import com.BookGroup.BookGroup.dto.SalaDTO;
import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Sala;
import com.BookGroup.BookGroup.mapper.Mapper;
import com.BookGroup.BookGroup.repository.SalaRepository;
import com.BookGroup.BookGroup.service.interfaces.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaServiceImpl implements SalaService {
    private final SalaRepository repository;
    @Override
    public List<SalaDTO> buscarTodos() {
        return repository.findAll().stream().map(Mapper::toSalaDTO).toList();
    }
    @Override
    public SalaDTO buscarPorId(Long id) {
        Sala sala = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la sala con el id : " + id)
        );
        return Mapper.toSalaDTO(sala);
    }
    @Override
    public SalaDTO crear(SalaDTO dot) {
        Sala nuevo = Mapper.toSala(dot);
        boolean conflicto = dot.getCapacidad()<0;
        if (conflicto){
            throw new IllegalArgumentException("La capacidad no puede ser menor que cero (0)");
        }
        Sala guardado = repository.save(nuevo);
        return Mapper.toSalaDTO(guardado);
    }
    @Override
    public SalaDTO actualizar(Long id, SalaDTO dto) {
        Sala sala = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la sala con el id : " + id)
        );
        Mapper.updateSala(dto,sala);
        Sala nuevo = repository.save(sala);
        return Mapper.toSalaDTO(nuevo);
    }
    @Override
    public SalaDTO eliminar(Long id) {
        Sala eliminar = repository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No se encontro la sala con el id : " + id)
        );
        boolean conflicto = repository.tieneReservacionesVigentes(id, Estado.VIGENTE);
        if(conflicto){
            throw new IllegalArgumentException("No se puede eliminar la sala pues tiene reservaciones en estado vigente");
        }
        repository.delete(eliminar);
        return Mapper.toSalaDTO(eliminar);
    }
}
