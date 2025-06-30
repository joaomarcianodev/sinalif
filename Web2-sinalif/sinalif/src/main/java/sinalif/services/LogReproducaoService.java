package sinalif.services;

import sinalif.dtos.LogReproducaoRecordDto;
import sinalif.models.LogReproducao;

import java.util.List;

public interface LogReproducaoService {
    public List<LogReproducao> listarLogReproducao();
    public LogReproducao detalharLogReproducao(Long id);
    public LogReproducao salvarLogReproducao(LogReproducaoRecordDto logRepDto);
    public void excluirLogReproducao(Long id);
}
