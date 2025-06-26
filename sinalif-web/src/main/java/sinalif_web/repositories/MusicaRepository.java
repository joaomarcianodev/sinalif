package sinalif_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sinalif_web.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}