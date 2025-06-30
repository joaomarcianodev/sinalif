package sinalif.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;   
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sinalif.models.PausaProgramada;
import sinalif.services.PausaProgramadaService;

@RestController
@RequestMapping("/api/pausasProgramadas")
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
	public PausaProgramada salvarPausaProgramada(@RequestBody PausaProgramada pausaProgramada) {
		return pausaProgramadaService.salvarPausaProgramada(pausaProgramada);
	}

	@PutMapping("/{id}")
	public PausaProgramada atualizarPausaProgramada(@PathVariable Long id, @RequestBody PausaProgramada pausaProgramada) {
		return pausaProgramadaService.atualizarPausaProgramada(id, pausaProgramada);
	}

	@DeleteMapping("/{id}")
	public void deletarPausaProgramada(@PathVariable Long id) {
		pausaProgramadaService.excluirPausaProgramada(id);
	}
}