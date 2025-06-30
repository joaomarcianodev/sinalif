package sinalif.services.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import sinalif.models.Etiqueta;
import sinalif.repositories.EtiquetaRepository;
import sinalif.services.EtiquetaService;

@Service
@RequiredArgsConstructor
public class EtiquetaServiceImpl implements EtiquetaService {
	private final EtiquetaRepository etiquetaRepository;

	public List<Etiqueta> getEtiqueta(){
		return etiquetaRepository.findAll();
	}

	public Etiqueta getEtiqueta(Long id) {
		return etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
	}

	public Etiqueta salvarEtiqueta(Etiqueta etiqueta){
		return etiquetaRepository.save(etiqueta);
	}

	public Etiqueta atualizarEtiqueta(Long id, Etiqueta etiquetaAtualizada) {
		Etiqueta etiquetaExistente = etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
		etiquetaExistente.setNome(etiquetaAtualizada.getNome());
		etiquetaExistente.setDuracao(etiquetaAtualizada.getDuracao());
		return etiquetaRepository.save(etiquetaExistente);
	}
	
	public void excluirEtiqueta(Etiqueta etiqueta) {
		etiquetaRepository.deleteById(etiqueta.getId_etiqueta());
	}

	public void excluirEtiqueta(Long id) {
		if (etiquetaRepository.existsById(id)) {
			etiquetaRepository.deleteById(id);
		} else {
			throw new RuntimeException("Etiqueta não encontrada com ID: " + id);
		}
	}
}