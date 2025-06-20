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
import sinalif_srv3.models.Alarme;
import sinalif_srv3.services.AlarmeService;

@RestController
@RequestMapping("/srv3/alarmes")
@CrossOrigin(origins = "*")
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
	 public Alarme salvarAlarme(@RequestBody AlarmeRecordDto alarmeRecordDto) {
	     return alarmeService.salvarAlarme(alarmeRecordDto);
	 }

	 @PutMapping("/{id}")
	 public Alarme atualizarAlarme(@PathVariable Long id, @RequestBody AlarmeRecordDto alarmeRecordDto) {
	     return alarmeService.atualizarAlarme(id, alarmeRecordDto);
	 }

	 @DeleteMapping("/{id}") 
	 public void deletarAlarme(@PathVariable Long id) {
	     alarmeService.excluirAlarme(id);
	 }
}