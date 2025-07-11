package sinalif.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import sinalif.dtos.AlarmeRecordDto;
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
    public List<Alarme> getAlarmes(){
		return alarmeRepository.findAll();
	}

	@Override
	public Alarme getAlarme(Long id) {
		return alarmeRepository.findById(id).get();
	}

	@Override
	public Alarme salvarAlarme(Alarme alarme){
		alarme.setEtiqueta(etiquetaRepository.getReferenceById(alarme.getEtiqueta().getId_etiqueta()));
		return alarmeRepository.save(alarme);
	}

	@Override
	public void excluirAlarme(Alarme alarme) {
		alarmeRepository.deleteById(alarme.getId_alarme());
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
	public Alarme atualizarAlarme(Long id, Alarme alarmeAtualizado) {
	    Alarme alarmeExistente = alarmeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Alarme não encontrado com ID: " + id));
	    alarmeExistente.setHorario_programado(alarmeAtualizado.getHorario_programado());
	    alarmeExistente.setDias_semana(alarmeAtualizado.getDias_semana());
	    alarmeExistente.setAtivo(alarmeAtualizado.isAtivo());
	    alarmeExistente.setPausado(alarmeAtualizado.isPausado());
	    alarmeExistente.setData_modificacao(LocalDateTime.now());
	    alarmeExistente.setEtiqueta(alarmeAtualizado.getEtiqueta());
	    return alarmeRepository.save(alarmeExistente);
	}

}
