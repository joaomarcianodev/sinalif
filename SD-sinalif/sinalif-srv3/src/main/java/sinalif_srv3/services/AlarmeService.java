package sinalif_srv3.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sinalif_srv3.dtos.AlarmeRecordDto;
import sinalif_srv3.models.Alarme;
import sinalif_srv3.repositories.AlarmeRepository;
import sinalif_srv3.repositories.EtiquetaRepository;

@Service
public class AlarmeService {
	private final AlarmeRepository alarmeRepository;
	private final EtiquetaRepository etiquetaRepository;
	
	public AlarmeService(AlarmeRepository alarmeRepository, EtiquetaRepository etiquetaRepository) {
		this.alarmeRepository = alarmeRepository;
		this.etiquetaRepository = etiquetaRepository;
	}
	
	public List<Alarme> getAlarmes(){
		return alarmeRepository.findAll();
	}
	
	public Alarme getAlarme(Long id) {
		return alarmeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Alarme não encontrado com ID: " + id));
	}
	
	public Alarme salvarAlarme(AlarmeRecordDto alarmeRecordDto) {
		Alarme alarme = new Alarme();
		alarme.setId_alarme(alarmeRecordDto.id_alarme());
		alarme.setEtiqueta(etiquetaRepository.findById(alarmeRecordDto.id_etiqueta()).get());
		alarme.setHorario_programado(alarmeRecordDto.horario_programado());
		alarme.setDias_semana(alarmeRecordDto.dias_semana());
		alarme.setAtivo(alarmeRecordDto.ativo());
		alarme.setPausado(alarmeRecordDto.pausado());
		alarme.setData_criacao(alarmeRecordDto.data_criacao());
		alarme.setData_modificacao(alarmeRecordDto.data_modificacao());

		return alarmeRepository.save(alarme);
	}

	public Alarme atualizarAlarme(Long id, AlarmeRecordDto alarmeRecordDto) {
		Alarme alarme = alarmeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Alarme não encontrado com ID: " + id));
		alarme.setEtiqueta(etiquetaRepository.findById(alarmeRecordDto.id_etiqueta()).get());
		alarme.setHorario_programado(alarmeRecordDto.horario_programado());
		alarme.setDias_semana(alarmeRecordDto.dias_semana());
		alarme.setAtivo(alarmeRecordDto.ativo());
		alarme.setPausado(alarmeRecordDto.pausado());
		alarme.setData_modificacao(alarmeRecordDto.data_modificacao());

		return alarmeRepository.save(alarme);
	}

	public void excluirAlarme(Long id) {
	    if (alarmeRepository.existsById(id)) {
	    	alarmeRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("Alarme não encontrada com ID: " + id);
	    }
	}
}
