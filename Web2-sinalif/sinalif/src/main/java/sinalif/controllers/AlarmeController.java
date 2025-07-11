package sinalif.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	public String listarAlarmes(@NotNull Model model) {
		model.addAttribute("alarmeList", IAlarmeService.listarAlarmes());
		return "pages/adm/alarmes/list";
	}

	@GetMapping("/{id}")
	public Alarme getAlarmeById(@PathVariable Long id) {
		return IAlarmeService.detalharAlarme(id);
	}

	@GetMapping("/create")
	public String pageAlarmesCreate(@NotNull Model model) {
		model.addAttribute("alarme", new Alarme());
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/adm/alarmes/create";
	}

	@PostMapping("/save")
	public String salvarAlarme(@ModelAttribute @Valid Alarme alarme, @NotNull BindingResult result, @NotNull Model model) {
		if (result.hasErrors()) {
			model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
			return "pages/adm/alarmes/create";
		}
		IAlarmeService.salvarAlarme(alarme);
		return "redirect:/adm/alarmes";
	}

	@GetMapping("/edit/{id}")
	public String atualizarAlarme(@PathVariable Long id_alarme, Model model) {
		model.addAttribute("alarme", IAlarmeService.detalharAlarme(id_alarme));
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/adm/alarmes/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirAlarme(@PathVariable Long id) {
 		IAlarmeService.excluirAlarme(id);
		return "redirect:/adm/alarmes";
	}
}