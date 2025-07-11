package sinalif.services;

import sinalif.models.Musica;

import java.util.List;

public interface MusicaService {
    public List<Musica> listarMusicas();
    public Musica detalharMusica(Long id);
    public Musica salvarMusica(Musica musica);
    public void excluirMusica(Long id);
}
