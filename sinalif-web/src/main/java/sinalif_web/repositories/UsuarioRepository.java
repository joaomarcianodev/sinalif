package sinalif_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sinalif_web.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para encontrar um usuário pelo email, que será usado no loadUserByUsername
    Optional<Usuario> findUsuarioByEmail(String email);
}
