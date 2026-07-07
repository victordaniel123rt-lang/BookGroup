package com.BookGroup.BookGroup.repository;

import com.BookGroup.BookGroup.entity.Empleado;
import com.BookGroup.BookGroup.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    @Query("SELECT COUNT(r) > 0 FROM Empleado e JOIN e.reservas r WHERE e.id = :empleadoId AND r.estado = :estado")
    boolean tieneReservacionesVigentes(@Param("empleadoId") Long empleadoId,@Param("estado") Estado estado);
}
