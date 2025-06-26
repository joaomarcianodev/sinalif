package sinalif_srv3.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sinalif_srv3.dtos.PausaProgramadaRecordDto;
import sinalif_srv3.models.Etiqueta;
import sinalif_srv3.models.PausaProgramada;
import sinalif_srv3.repositories.PausaProgramadaRepository;

@Service
public class PausaProgramadaService {
	private final PausaProgramadaRepository pausaProgramadaRepository;

	public PausaProgramadaService(PausaProgramadaRepository pausaProgramadaRepository) {
		this.pausaProgramadaRepository = pausaProgramadaRepository;
	}

	public List<PausaProgramada> getPausasProgramadas(){
		return pausaProgramadaRepository.findAll();
	}

	public PausaProgramada getPausaProgramada(Long id) {
		return pausaProgramadaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
	}

	public PausaProgramada salvarPausaProgramada(PausaProgramadaRecordDto pausaProgramadaRecordDto){
		PausaProgramada pausa = new PausaProgramada();
		pausa.setId_pausa(pausaProgramadaRecordDto.id_pausa());
		pausa.setData_inicio(pausaProgramadaRecordDto.data_inicio());
		pausa.setData_fim(pausaProgramadaRecordDto.data_fim());
		pausa.setHora_inicio(pausaProgramadaRecordDto.hora_inicio());
		pausa.setHora_fim(pausaProgramadaRecordDto.hora_fim());
		pausa.setAtivo(pausaProgramadaRecordDto.ativo());
		pausa.setData_criacao(pausaProgramadaRecordDto.data_criacao());

		return pausaProgramadaRepository.save(pausa);
	}

	public PausaProgramada atualizarPausaProgramada(Long id, PausaProgramadaRecordDto pausaProgramadaRecordDto){
		PausaProgramada pausa = pausaProgramadaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Pausa programada não encontrada com ID: " + id));
		pausa.setData_inicio(pausaProgramadaRecordDto.data_inicio());
		pausa.setData_fim(pausaProgramadaRecordDto.data_fim());
		pausa.setHora_inicio(pausaProgramadaRecordDto.hora_inicio());
		pausa.setHora_fim(pausaProgramadaRecordDto.hora_fim());
		pausa.setAtivo(pausaProgramadaRecordDto.ativo());
		pausa.setData_criacao(pausaProgramadaRecordDto.data_criacao());

		return pausaProgramadaRepository.save(pausa);
	}

	public void excluirPausaProgramada(Long id) {
		if (pausaProgramadaRepository.existsById(id)) {
			pausaProgramadaRepository.deleteById(id);
		} else {
			throw new RuntimeException("Pausa programada não encontrada com ID: " + id);
		}
	}
}