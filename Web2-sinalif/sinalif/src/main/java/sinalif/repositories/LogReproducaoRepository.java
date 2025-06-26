package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sinalif.models.LogReproducao;

public interface LogReproducaoRepository extends JpaRepository<LogReproducao, Long> {
}
