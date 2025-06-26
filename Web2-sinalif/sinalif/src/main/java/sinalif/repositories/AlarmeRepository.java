package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sinalif.models.Alarme;

public interface AlarmeRepository extends JpaRepository<Alarme, Long>{

}
