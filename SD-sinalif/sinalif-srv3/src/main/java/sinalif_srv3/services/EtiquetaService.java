package sinalif_srv3.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sinalif_srv3.dtos.AlarmeRecordDto;
import sinalif_srv3.dtos.EtiquetaRecordDto;
import sinalif_srv3.models.Alarme;
import sinalif_srv3.models.Etiqueta;
import sinalif_srv3.repositories.EtiquetaRepository;

@Service
public class EtiquetaService {
	private final EtiquetaRepository etiquetaRepository;

	public EtiquetaService(EtiquetaRepository etiquetaRepository) {
		this.etiquetaRepository = etiquetaRepository;
	}

	public List<Etiqueta> getEtiqueta(){
		return etiquetaRepository.findAll();
	}

	public Etiqueta getEtiqueta(Long id) {
		return etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
	}

	public Etiqueta salvarEtiqueta(EtiquetaRecordDto etiquetaRecordDto){
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setId_etiqueta(etiquetaRecordDto.id_etiqueta());
		etiqueta.setNome(etiquetaRecordDto.nome());
		etiqueta.setDuracao(etiquetaRecordDto.duracao());

		return etiquetaRepository.save(etiqueta);
	}

	public Etiqueta atualizarEtiqueta(Long id, EtiquetaRecordDto etiquetaRecordDto) {
		Etiqueta etiqueta = etiquetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Etiqueta não encontrada com ID: " + id));
		etiqueta.setNome(etiquetaRecordDto.nome());
		etiqueta.setDuracao(etiquetaRecordDto.duracao());

		return etiquetaRepository.save(etiqueta);
	}

	public void excluirEtiqueta(Long id) {
		if (etiquetaRepository.existsById(id)) {
			etiquetaRepository.deleteById(id);
		} else {
			throw new RuntimeException("Etiqueta não encontrada com ID: " + id);
		}
	}
}