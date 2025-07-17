package sinalif.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sinalif.models.Alarme;
import sinalif.repositories.AlarmeRepository;
import sinalif.repositories.EtiquetaRepository;
import sinalif.services.AlarmeService;

@Service
@RequiredArgsConstructor
public class AlarmeServiceImpl implements AlarmeService {
	@Autowired
	private AlarmeRepository alarmeRepository;

	@Autowired
	private EtiquetaRepository etiquetaRepository;

	@Override
    public List<Alarme> listarAlarmes(){
		return alarmeRepository.findAll();
	}

	@Override
	public Alarme detalharAlarme(Long id) {
		return alarmeRepository.findById(id).get();
	}

	@Override
	public Alarme salvarAlarme(Alarme alarme){
		alarme.setEtiqueta(etiquetaRepository.getReferenceById(alarme.getEtiqueta().getIdEtiqueta()));
		List<String> diasSelecionados = alarme.getDiasSemanaCheckbox();

		if (diasSelecionados != null && !diasSelecionados.isEmpty()) {
			String diasComoString = String.join(",", diasSelecionados);
			alarme.setDiasSemana(diasComoString);
		} else {
			alarme.setDiasSemana(null);
			throw new RuntimeException("Alarme não pode ter dias nulos.");
		}

		return alarmeRepository.save(alarme);
	}

	@Override
	public Alarme atualizarAlarme(Long id, Alarme alarmeAtualizado) {
	    Alarme alarmeExistente = alarmeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Alarme não encontrado com ID: " + id));
	    alarmeExistente.setHorarioProgramado(alarmeAtualizado.getHorarioProgramado());
	    alarmeExistente.setDiasSemana(alarmeAtualizado.getDiasSemana());
	    alarmeExistente.setAtivo(alarmeAtualizado.isAtivo());
	    alarmeExistente.setPausado(alarmeAtualizado.isPausado());
	    alarmeExistente.setDataModificacao(LocalDateTime.now());
	    alarmeExistente.setEtiqueta(alarmeAtualizado.getEtiqueta());
	    return alarmeRepository.save(alarmeExistente);
	}

	@Override
	public void excluirAlarme(Long id) {
		if (alarmeRepository.existsById(id)) {
			alarmeRepository.deleteById(id);
		} else {
			throw new RuntimeException("Alarme não encontrada com ID: " + id);
		}
	}

	@Override
	public void excluirAlarme(Alarme alarme) {
		alarmeRepository.deleteById(alarme.getIdAlarme());
	}
}
