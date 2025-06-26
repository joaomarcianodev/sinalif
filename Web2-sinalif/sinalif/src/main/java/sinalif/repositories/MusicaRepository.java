package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sinalif.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
