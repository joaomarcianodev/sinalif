package sinalif.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sinalif.dtos.AlarmeRecordDto;
import sinalif.models.Alarme;
import sinalif.services.AlarmeService;
import sinalif.services.EtiquetaService;

@Controller
@RequestMapping("/adm/alarmes")
public class AlarmeController {
	@Autowired
	private AlarmeService IAlarmeService;
	@Autowired
	private EtiquetaService IEtiquetaService;

	@GetMapping()
	public String pageAlarmes(@NotNull Model model) {
		model.addAttribute("alarmeList", IAlarmeService.getAlarmes());
		return "pages/adm/alarmes/list";
	}

	@GetMapping("/create")
	public String pageAlarmesCreate(@NotNull Model model) {
		model.addAttribute("alarme", new Alarme());
		model.addAttribute("etiquetaList", IEtiquetaService.getEtiquetas());
		return "pages/adm/alarmes/create";
	}

	@PostMapping("/save")
	public String postMethodName(@ModelAttribute @Valid Alarme alarme, @NotNull BindingResult result, @NotNull Model model) {
		if (result.hasErrors()) {
			model.addAttribute("etiquetaList", IEtiquetaService.getEtiquetas());
			return "pages/adm/alarmes/create";
		}
		IAlarmeService.salvarAlarme(alarme);
		return "redirect:/adm/alarmes";
	}

	@GetMapping("/{id}")
	public Alarme getAlarmeById(@PathVariable Long id) {
		return IAlarmeService.getAlarme(id);
	}

	@GetMapping("/edit/{id_alarme}")
	public String atualizarAlarme(@PathVariable Long id_alarme, Model model) {
		model.addAttribute("alarme", IAlarmeService.getAlarme(id_alarme));
		model.addAttribute("etiquetaList", IEtiquetaService.getEtiquetas());
		return "pages/adm/alarmes/create";
	}

	@GetMapping("/delete/{id_alarme}")
	public String deletarAlarme(@PathVariable Long id_alarme) {
 		IAlarmeService.excluirAlarme(id_alarme);
		return "redirect:/adm/alarmes";
	}

	/*@GetMapping
	 public List<Alarme> getAlarmes(){
	     return IAlarmeService.getAlarmes();
	 }*/

	/*@PostMapping
	public Alarme salvaAlarme(@RequestBody Alarme alarme) {
		return IAlarmeService.salvarAlarme(alarme);
	}*/
}