package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinalif.models.PausaProgramada;
import sinalif.repositories.PausaProgramadaRepository;
import sinalif.services.PausaProgramadaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PausaProgramadaServiceImpl implements PausaProgramadaService{
    private final PausaProgramadaRepository pausaProgramadaRepository;

    public List<PausaProgramada> getPausasProgramadas(){
        return pausaProgramadaRepository.findAll();
    }

    public PausaProgramada getPausaProgramada(Long id) {
        return pausaProgramadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
    }

    public PausaProgramada salvarPausaProgramada(PausaProgramada pausaProgramada){
        return pausaProgramadaRepository.save(pausaProgramada);
    }

    public PausaProgramada atualizarPausaProgramada(Long id, PausaProgramada pausaProgramadaAtualizada) {
        PausaProgramada pausaExistente = pausaProgramadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
        pausaExistente.setData_inicio(pausaProgramadaAtualizada.getData_inicio());
        pausaExistente.setData_fim(pausaProgramadaAtualizada.getData_fim());
        pausaExistente.setHora_inicio(pausaProgramadaAtualizada.getHora_inicio());
        pausaExistente.setHora_fim(pausaProgramadaAtualizada.getHora_fim());
        pausaExistente.setAtivo(pausaProgramadaAtualizada.isAtivo());
        return pausaProgramadaRepository.save(pausaExistente);
    }

    public void excluirPausaProgramada(PausaProgramada pausaProgramada) {
        pausaProgramadaRepository.deleteById(pausaProgramada.getId_pausa());
    }

    public void excluirPausaProgramada(Long id) {
        if (pausaProgramadaRepository.existsById(id)) {
            pausaProgramadaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pausa programada não encontrada com ID: " + id);
        }
    }
}
