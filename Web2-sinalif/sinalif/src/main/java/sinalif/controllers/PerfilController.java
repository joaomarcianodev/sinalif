package sinalif.controllers;

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

import sinalif.models.Perfil;
import sinalif.services.PerfilService;

@RestController
@RequestMapping("/api/perfis")
public class PerfilController {
	@Autowired
	private PerfilService perfilService;

	@CrossOrigin(origins = "*")
	@GetMapping
	public List<Perfil> getPerfis(){
		return perfilService.getPerfis();
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public Perfil getPerfilById(@PathVariable Long id){
		return perfilService.getPerfil(id);
	}

	@CrossOrigin(origins = "*")
	@PostMapping
	public Perfil salvarPerfil(@RequestBody Perfil perfil) {
		return perfilService.salvarPerfil(perfil);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	public Perfil atualizarPerfil(@PathVariable Long id, @RequestBody Perfil perfil) {
		return perfilService.atualizarPerfil(id, perfil);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public void deletarPerfil(@PathVariable Long id) {
		perfilService.excluirPerfil(id);
	}
}