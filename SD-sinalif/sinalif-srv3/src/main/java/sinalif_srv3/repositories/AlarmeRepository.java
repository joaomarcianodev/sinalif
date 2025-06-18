package sinalif_srv3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sinalif_srv3.models.Alarme;

public interface AlarmeRepository extends JpaRepository<Alarme, Long>{

}
