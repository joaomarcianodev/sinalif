package sinalif_srv1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sinalif_srv1.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
