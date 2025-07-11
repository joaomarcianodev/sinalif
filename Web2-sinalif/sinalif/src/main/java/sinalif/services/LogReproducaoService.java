package sinalif.services;

import sinalif.models.LogReproducao;

import java.util.List;

public interface LogReproducaoService {
    public List<LogReproducao> listarLogReproducao();
    public LogReproducao detalharLogReproducao(Long id);
    public LogReproducao salvarLogReproducao(LogReproducao logRep);
    public void excluirLogReproducao(Long id);
}
