package prova.sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import prova.sinalif.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
