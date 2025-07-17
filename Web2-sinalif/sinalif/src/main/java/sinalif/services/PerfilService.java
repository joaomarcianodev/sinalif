package sinalif.services;

import sinalif.models.Perfil;

import java.util.List;

public interface PerfilService {
    public List<Perfil> listarPerfis();
    public Perfil detalharPerfil(Long id);
    public Perfil salvarPerfil(Perfil perfil);
    public void excluirPerfil(Long id);
    public Perfil atualizarPerfil(Long id, Perfil perfilAtualizado);
    public void excluirPerfil(Perfil perfil);
}
