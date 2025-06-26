package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sinalif.models.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para encontrar um usuário pelo email, que será usado no loadUserByUsername
    Optional<Usuario> findUsuarioByEmail(String email);
}
