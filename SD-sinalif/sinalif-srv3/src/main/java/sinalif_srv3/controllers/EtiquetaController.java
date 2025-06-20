package sinalif_srv3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sinalif_srv3.dtos.AlarmeRecordDto;
import sinalif_srv3.dtos.EtiquetaRecordDto;
import sinalif_srv3.models.Alarme;
import sinalif_srv3.models.Etiqueta;
import sinalif_srv3.services.EtiquetaService;

@RestController
@RequestMapping("/srv3/etiquetas")
@CrossOrigin(origins = "*")
public class EtiquetaController {
	@Autowired
	private EtiquetaService etiquetaService;

	@GetMapping
	public List<Etiqueta> getEtiqueta(){
		return etiquetaService.getEtiqueta();
	}

	@GetMapping("/{id}")
	public Etiqueta getEtiquetaById(@PathVariable Long id){
		return etiquetaService.getEtiqueta(id);
	}

	@PostMapping
	public Etiqueta salvarEtiqueta(@RequestBody EtiquetaRecordDto etiquetaRecordDto) {
		return etiquetaService.salvarEtiqueta(etiquetaRecordDto);
	}

	@PutMapping("/{id}")
	public Etiqueta atualizarEtiqueta(@PathVariable Long id, @RequestBody EtiquetaRecordDto etiquetaRecordDto) {
		return etiquetaService.atualizarEtiqueta(id, etiquetaRecordDto);
	}

	@DeleteMapping("/{id}")
	public void deletarEtiqueta(@PathVariable Long id) {
		etiquetaService.excluirEtiqueta(id);
	}
}