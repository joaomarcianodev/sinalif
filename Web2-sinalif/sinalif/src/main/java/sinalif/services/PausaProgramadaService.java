package sinalif.services;

import sinalif.models.PausaProgramada;

import java.util.List;

public interface PausaProgramadaService {
    List<PausaProgramada> getPausasProgramadas();
    public PausaProgramada getPausaProgramada(Long id);
    public PausaProgramada salvarPausaProgramada(PausaProgramada pausaProgramada);
    public PausaProgramada atualizarPausaProgramada(Long id, PausaProgramada pausaProgramadaAtualizada);
    public void excluirPausaProgramada(PausaProgramada pausaProgramada);
    public void excluirPausaProgramada(Long id);
}
