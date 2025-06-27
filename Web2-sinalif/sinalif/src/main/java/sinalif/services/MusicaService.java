package sinalif.services;

import sinalif.dtos.MusicaRecordDto;
import sinalif.models.Musica;

import java.util.List;

public interface MusicaService {
    public List<Musica> listarMusicas();
    public Musica detalharMusica(Long id);
    public Musica salvarMusica(MusicaRecordDto musicaRecordDto);
    public void excluirMusica(Long id);
}
