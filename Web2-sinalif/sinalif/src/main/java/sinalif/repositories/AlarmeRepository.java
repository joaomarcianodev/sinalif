package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sinalif.models.Alarme;

import java.util.List;

@Repository
public interface AlarmeRepository extends JpaRepository<Alarme, Long>{

    @Query(
            value = "SELECT * FROM alarme " +
                    "WHERE " +
                    "  ativo = true AND (pausado = false OR pausado IS NULL) " +
                    "  AND dias_semana LIKE '%' || (EXTRACT(DOW FROM NOW()) + 1)::text || '%' " +
                    "  AND horario_programado <= CAST(NOW() AS time) " +
                    "  AND (data_ultima_execucao IS NULL OR DATE(data_ultima_execucao) < CURRENT_DATE)",
            nativeQuery = true
    )
    List<Alarme> findAlarmesPendentesParaAgora();
}
