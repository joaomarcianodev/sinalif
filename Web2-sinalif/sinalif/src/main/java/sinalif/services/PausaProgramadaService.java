package sinalif.services;

import sinalif.models.PausaProgramada;

import java.util.List;

public interface PausaProgramadaService {
    public List<PausaProgramada> listarPausasProgramadas();
    public PausaProgramada detalharPausaProgramada(Long id);
    public PausaProgramada salvarPausaProgramada(PausaProgramada pausaProgramada);
    public PausaProgramada atualizarPausaProgramada(Long id, PausaProgramada pausaProgramadaAtualizada);
    public void excluirPausaProgramada(Long id);
    public void excluirPausaProgramada(PausaProgramada pausaProgramada);
}
