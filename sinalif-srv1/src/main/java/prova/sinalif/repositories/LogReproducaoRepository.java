package prova.sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import prova.sinalif.models.LogReproducao;

public interface LogReproducaoRepository extends JpaRepository<LogReproducao, Long> {
}
