package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sinalif.models.LogReproducao;

@Repository
public interface LogReproducaoRepository extends JpaRepository<LogReproducao, Long> {
}
