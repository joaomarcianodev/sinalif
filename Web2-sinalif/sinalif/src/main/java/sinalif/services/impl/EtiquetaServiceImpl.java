package sinalif.services.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sinalif.models.Etiqueta;
import sinalif.repositories.EtiquetaRepository;
import sinalif.services.EtiquetaService;

@Service
@RequiredArgsConstructor
public class EtiquetaServiceImpl implements EtiquetaService {
	@Autowired
	private EtiquetaRepository etiquetaRepository;

	@Override
	public List<Etiqueta> listarEtiquetas(){
		return etiquetaRepository.findAll();
	}

	@Override
	public Etiqueta detalharEtiqueta(Long id) {
		return etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
	}

	@Override
	public Etiqueta salvarEtiqueta(Etiqueta etiqueta){
		return etiquetaRepository.save(etiqueta);
	}

	@Override
	public Etiqueta atualizarEtiqueta(Long id, Etiqueta etiquetaAtualizada) {
		Etiqueta etiquetaExistente = etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
		etiquetaExistente.setNome(etiquetaAtualizada.getNome());
		etiquetaExistente.setDuracao(etiquetaAtualizada.getDuracao());
		return etiquetaRepository.save(etiquetaExistente);
	}

	@Override
	public void excluirEtiqueta(Etiqueta etiqueta) {
		etiquetaRepository.deleteById(etiqueta.getIdEtiqueta());
	}

	@Override
	public void excluirEtiqueta(Long id) {
		if (etiquetaRepository.existsById(id)) {
			etiquetaRepository.deleteById(id);
		} else {
			throw new RuntimeException("Etiqueta não encontrada com ID: " + id);
		}
	}
}