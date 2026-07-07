package com.BookGroup.BookGroup.repository;

import com.BookGroup.BookGroup.entity.Estado;
import com.BookGroup.BookGroup.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r " +
            "WHERE r.sala.id = :salaId " +
            "AND r.estado = :estado " + // O r.activo = true, según tu modelo
            "AND :horaInicio < r.horaFin " +
            "AND r.horaInicio < :horaFin")
    boolean existeTraslape(@Param("salaId") Long salaId,
                           @Param("horaInicio") LocalDateTime horaInicio,
                           @Param("horaFin") LocalDateTime horaFin,
                           @Param("estado") Estado estado);
}
