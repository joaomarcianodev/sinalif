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

import sinalif_srv3.dtos.PausaProgramadaRecordDto;
import sinalif_srv3.dtos.PerfilRecordDto;
import sinalif_srv3.models.PausaProgramada;
import sinalif_srv3.models.Perfil;
import sinalif_srv3.services.PerfilService;

@RestController
@RequestMapping("/srv3/perfis")
@CrossOrigin(origins = "*")
public class PerfilController {
	@Autowired
	private PerfilService perfilService;

	@GetMapping
	public List<Perfil> getPerfis(){
		return perfilService.getPerfis();
	}

	@GetMapping("/{id}")
	public Perfil getPerfilById(@PathVariable Long id){
		return perfilService.getPerfil(id);
	}

	@PostMapping
	public Perfil salvarPerfil(@RequestBody PerfilRecordDto perfilRecordDto) {
		return perfilService.salvarPerfil(perfilRecordDto);
	}

	@PutMapping("/{id}")
	public Perfil atualizarPerfil(@PathVariable Long id, @RequestBody PerfilRecordDto perfilRecordDto) {
		return perfilService.atualizarPerfil(id, perfilRecordDto);
	}

	@DeleteMapping("/{id}")
	public void deletarPerfil(@PathVariable Long id) {
		perfilService.excluirPerfil(id);
	}
}