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

import sinalif_srv3.dtos.EtiquetaRecordDto;
import sinalif_srv3.dtos.PausaProgramadaRecordDto;
import sinalif_srv3.models.Etiqueta;
import sinalif_srv3.models.PausaProgramada;
import sinalif_srv3.services.PausaProgramadaService;

@RestController
@RequestMapping("/srv3/pausas")
@CrossOrigin(origins = "*")
public class PausaProgramadaController {
	@Autowired
	private PausaProgramadaService pausaProgramadaService;

	@GetMapping
	public List<PausaProgramada> getPausasProgramadas(){
		return pausaProgramadaService.getPausasProgramadas();
	}

	@GetMapping("/{id}")
	public PausaProgramada getPausaProgramadaById(@PathVariable Long id){
		return pausaProgramadaService.getPausaProgramada(id);
	}

	@PostMapping
	public PausaProgramada salvarPausaProgramada(@RequestBody PausaProgramadaRecordDto pausaProgramadaRecordDto) {
		return pausaProgramadaService.salvarPausaProgramada(pausaProgramadaRecordDto);
	}

	@PutMapping("/{id}")
	public PausaProgramada atualizarPausaProgramada(@PathVariable Long id, @RequestBody PausaProgramadaRecordDto pausaProgramadaRecordDto) {
		return pausaProgramadaService.atualizarPausaProgramada(id, pausaProgramadaRecordDto);
	}

	@DeleteMapping("/{id}")
	public void deletarPausaProgramada(@PathVariable Long id) {
		pausaProgramadaService.excluirPausaProgramada(id);
	}
}