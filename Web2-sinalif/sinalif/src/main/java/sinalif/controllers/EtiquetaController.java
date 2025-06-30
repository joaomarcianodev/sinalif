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

import sinalif.models.Etiqueta;
import sinalif.services.impl.EtiquetaServiceImpl;

@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaController {
	@Autowired
	private EtiquetaServiceImpl etiquetaService;

	@GetMapping
	public List<Etiqueta> getEtiqueta(){
		return etiquetaService.getEtiqueta();
	}

	@GetMapping("/{id}")
	public Etiqueta getEtiquetaById(@PathVariable Long id){
		return etiquetaService.getEtiqueta(id);
	}

	@PostMapping
	public Etiqueta salvarEtiqueta(@RequestBody Etiqueta etiqueta) {
		return etiquetaService.salvarEtiqueta(etiqueta);
	}

	@PutMapping("/{id}")
	public Etiqueta atualizarEtiqueta(@PathVariable Long id, @RequestBody Etiqueta etiqueta) {
		return etiquetaService.atualizarEtiqueta(id, etiqueta);
	}

	@DeleteMapping("/{id}")
	public void deletarEtiqueta(@PathVariable Long id) {
		etiquetaService.excluirEtiqueta(id);
	}
}