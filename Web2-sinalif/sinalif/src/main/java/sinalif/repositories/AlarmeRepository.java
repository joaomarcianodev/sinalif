package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import sinalif.models.Alarme;

@Repository
public interface AlarmeRepository extends JpaRepository<Alarme, Long>{

}
