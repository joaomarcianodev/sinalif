package sinalif.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import sinalif.models.Alarme;
import sinalif.repositories.AlarmeRepository;

@Service
public class AlarmeService {
	private final AlarmeRepository alarmeRepository;
	
	public AlarmeService(AlarmeRepository alarmeRepository) {
		this.alarmeRepository = alarmeRepository;
	}
	
	public List<Alarme> getAlarmes(){
		return alarmeRepository.findAll();
	}
	
	public Alarme getAlarme(Long id) {
		return alarmeRepository.findById(id).get();
	}
	
	public Alarme salvarAlarme(Alarme alarme){
		return alarmeRepository.save(alarme);
	}
	
	public void excluirAlarme(Alarme alarme) {
		alarmeRepository.deleteById(alarme.getId_alarme());
	}
	
	public void excluirAlarme(Long id) {
	    if (alarmeRepository.existsById(id)) {
	    	alarmeRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("Alarme não encontrada com ID: " + id);
	    }
	}
	
	public Alarme atualizarAlarme(Long id, Alarme alarmeAtualizado) {
	    Alarme alarmeExistente = alarmeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Alarme não encontrado com ID: " + id));
	    alarmeExistente.setHorario_programado(alarmeAtualizado.getHorario_programado());
	    alarmeExistente.setDias_semana(alarmeAtualizado.getDias_semana());
	    alarmeExistente.setAtivo(alarmeAtualizado.isAtivo());
	    alarmeExistente.setPausado(alarmeAtualizado.isPausado());
	    alarmeExistente.setData_modificacao(LocalDate.now());
	    alarmeExistente.setEtiqueta(alarmeAtualizado.getEtiqueta()); 
	    return alarmeRepository.save(alarmeExistente);
	}

}
