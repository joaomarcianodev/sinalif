package sinalif_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sinalif_web.models.Alarme;

public interface AlarmeRepository extends JpaRepository<Alarme, Long>{

}
