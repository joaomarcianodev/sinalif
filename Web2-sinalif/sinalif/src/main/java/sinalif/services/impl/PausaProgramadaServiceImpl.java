package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalif.models.PausaProgramada;
import sinalif.repositories.PausaProgramadaRepository;
import sinalif.services.PausaProgramadaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PausaProgramadaServiceImpl implements PausaProgramadaService{
    @Autowired
    private PausaProgramadaRepository pausaProgramadaRepository;

    @Override
    public List<PausaProgramada> listarPausasProgramadas(){
        return pausaProgramadaRepository.findAll();
    }

    @Override
    public PausaProgramada detalharPausaProgramada(Long id) {
        return pausaProgramadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
    }

    @Override
    public PausaProgramada salvarPausaProgramada(PausaProgramada pausaProgramada){
        return pausaProgramadaRepository.save(pausaProgramada);
    }

    @Override
    public PausaProgramada atualizarPausaProgramada(Long id, PausaProgramada pausaProgramadaAtualizada) {
        PausaProgramada pausaExistente = pausaProgramadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
        pausaExistente.setDataHoraInicio(pausaProgramadaAtualizada.getDataHoraInicio());
        pausaExistente.setDataHoraFim(pausaProgramadaAtualizada.getDataHoraFim());
        pausaExistente.setAtivo(pausaProgramadaAtualizada.isAtivo());
        return pausaProgramadaRepository.save(pausaExistente);
    }

    @Override
    public void excluirPausaProgramada(PausaProgramada pausaProgramada) {
        pausaProgramadaRepository.deleteById(pausaProgramada.getIdPausa());
    }

    @Override
    public void excluirPausaProgramada(Long id) {
        if (pausaProgramadaRepository.existsById(id)) {
            pausaProgramadaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pausa programada não encontrada com ID: " + id);
        }
    }
}
