package com.BookGroup.BookGroup.repository;


import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaRepository extends JpaRepository<Sala,Long> {
    @Query("SELECT COUNT(r) > 0 FROM Sala s JOIN s.reservas r WHERE s.id = :salaId AND r.estado = :estado")
    boolean tieneReservacionesVigentes(@Param("salaId") Long salaId,@Param("estado") Estado estado);
}
