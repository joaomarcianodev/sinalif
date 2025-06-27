package sinalif.services;

import sinalif.models.Perfil;

import java.util.List;

public interface PerfilService {
    public List<Perfil> getPerfis();
    Perfil salvarPerfil(Perfil perfil);
    Perfil getPerfil(Long id);
    Perfil atualizarPerfil(Long id, Perfil perfilAtualizado);
    void excluirPerfil(Perfil perfil);
    public void excluirPerfil(Long id);
}
