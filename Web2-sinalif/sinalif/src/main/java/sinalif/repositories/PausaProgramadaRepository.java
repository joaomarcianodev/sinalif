package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import sinalif.models.PausaProgramada;

@Repository
public interface PausaProgramadaRepository extends JpaRepository<PausaProgramada, Long>{

}
