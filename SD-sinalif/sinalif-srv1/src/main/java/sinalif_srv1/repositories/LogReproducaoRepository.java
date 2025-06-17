package sinalif_srv1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sinalif_srv1.models.LogReproducao;

public interface LogReproducaoRepository extends JpaRepository<LogReproducao, Long> {
}
