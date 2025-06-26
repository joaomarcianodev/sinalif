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

import sinalif.models.Alarme;
import sinalif.services.AlarmeService;


@RestController
@RequestMapping("/api/alarmes")
public class AlarmeController {
	 @Autowired
	 private AlarmeService alarmeService;

	 @GetMapping
	 public List<Alarme> getAlarmes(){
	     return alarmeService.getAlarmes();
	 }

	 @GetMapping("/{id}")
	 public Alarme getAlarmeById(@PathVariable Long id) {
	     return alarmeService.getAlarme(id);
	 }

	 @PostMapping
	 public Alarme salvarAlarme(@RequestBody Alarme alarme) {
	     return alarmeService.salvarAlarme(alarme);
	 }

	 @PutMapping("/{id}") 
	 public Alarme atualizarAlarme(@PathVariable Long id, @RequestBody Alarme alarme) {
	     return alarmeService.atualizarAlarme(id, alarme);
	 }

	 @DeleteMapping("/{id}") 
	 public void deletarAlarme(@PathVariable Long id) {
	     alarmeService.excluirAlarme(id);
	 }
}