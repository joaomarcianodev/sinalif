package sinalif.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import sinalif.models.Alarme;
import sinalif.services.AlarmeService;

@RestController
@RequestMapping("/api/alarmes")
public class AlarmeController {
	 @Autowired
	 private AlarmeService IalarmeService;

	 @GetMapping
	 public List<Alarme> getAlarmes(){
	     return IalarmeService.getAlarmes();
	 }

	 @GetMapping("/{id}")
	 public Alarme getAlarmeById(@PathVariable Long id) {
	     return IalarmeService.getAlarme(id);
	 }

	@PostMapping
	public Alarme salvaAlarme(@RequestBody Alarme alarme) {
		return IalarmeService.salvarAlarme(alarme);
	}

	 @PutMapping("/{id}") 
	 public Alarme atualizarAlarme(@PathVariable Long id, @RequestBody Alarme alarme) {
	     return IalarmeService.atualizarAlarme(id, alarme);
	 }

	 @DeleteMapping("/{id}") 
	 public void deletarAlarme(@PathVariable Long id) {
		 IalarmeService.excluirAlarme(id);
	 }
}