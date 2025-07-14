package sinalif.services;

import sinalif.models.Sugestao;
import java.util.List;

public interface SugestaoService {
    List<Sugestao> listarSugestoes();
    Sugestao detalharSugestao(Long id);
    Sugestao criarSugestao(Sugestao sugestao);
    Sugestao atualizarSugestao(Long id, Sugestao sugestaoDetalhes);
    void deletarSugestao(Long id);
    List<Sugestao> listarSugestoesPorUsuario(Long idUsuario);
    Sugestao atualizarStatusSugestao(Long idSugestao, String novoStatus);
}