package sinalif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import sinalif.models.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
