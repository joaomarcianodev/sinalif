package sinalif.services;

import sinalif.models.Sugestao;
import java.util.List;

public interface SugestaoService {
    public List<Sugestao> listarSugestoes();
    public Sugestao detalharSugestao(Long id);
    public Sugestao salvarSugestao(Sugestao sugestao);
    public Sugestao atualizarSugestao(Long id, Sugestao sugestaoDetalhes);
    public Sugestao atualizarStatusSugestao(Long idSugestao, String novoStatus);
    public void excluirSugestao(Long id);
    public List<Sugestao> listarSugestoesPorUsuario(Long idUsuario);
}