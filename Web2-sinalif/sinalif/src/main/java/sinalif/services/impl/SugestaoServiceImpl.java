package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalif.models.Sugestao;
import sinalif.models.Usuario;
import sinalif.repositories.SugestaoRepository;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.SugestaoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SugestaoServiceImpl implements SugestaoService {

    @Autowired
    private SugestaoRepository sugestaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Sugestao> listarSugestoes() {
        return sugestaoRepository.findAll();
    }

    @Override
    public List<Sugestao> listarMinhasSugestoes(Long idUsuario) {
        return sugestaoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Sugestao detalharSugestao(Long id) {
        return sugestaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada com ID: " + id));
    }

    @Override
    public Sugestao salvarSugestao(Sugestao sugestao) {
        Usuario usuario = usuarioRepository.findById(sugestao.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + sugestao.getUsuario().getIdUsuario()));
        sugestao.setUsuario(usuario);
        sugestao.setStatus_sugestao("Pendente");
        sugestao.setData_sugestao(LocalDateTime.now());
        sugestao.setData_analise(null);
        return sugestaoRepository.save(sugestao);
    }

    @Override
    public Sugestao atualizarSugestao(Long id, Sugestao sugestaoDetalhes) {
        return sugestaoRepository.findById(id)
                .map(sugestao -> {
                    sugestao.setUrl_sugerida(sugestaoDetalhes.getUrl_sugerida());
                    if (sugestaoDetalhes.getStatus_sugestao() != null && !sugestaoDetalhes.getStatus_sugestao().isEmpty()) {
                        sugestao.setStatus_sugestao(sugestaoDetalhes.getStatus_sugestao());
                    }
                    if (sugestaoDetalhes.getData_analise() != null) {
                        sugestao.setData_analise(sugestaoDetalhes.getData_analise());
                    }
                    return sugestaoRepository.save(sugestao);
                })
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada com ID: " + id));
    }

    @Override
    public void excluirSugestao(Long id) {
        if (sugestaoRepository.existsById(id)) {
            sugestaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sugestão não encontrada com ID: " + id);
        }
    }

    @Override
    public List<Sugestao> listarSugestoesPorUsuario(Long idUsuario) {
        return sugestaoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Sugestao atualizarStatusSugestao(Long idSugestao, String novoStatus) {
        return sugestaoRepository.findById(idSugestao)
                .map(sugestao -> {
                    sugestao.setStatus_sugestao(novoStatus);
                    sugestao.setData_analise(LocalDateTime.now());
                    return sugestaoRepository.save(sugestao);
                })
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada com ID: " + idSugestao));
    }
}