package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sinalif.models.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
}