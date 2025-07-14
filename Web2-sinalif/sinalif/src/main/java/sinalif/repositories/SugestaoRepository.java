package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sinalif.models.Sugestao;
import java.util.List;

public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
    List<Sugestao> findByUsuarioIdUsuario(Long idUsuario);
}