package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sinalif.models.Alarme;
import sinalif.services.AlarmeService;
import sinalif.services.EtiquetaService;

@Controller
@RequestMapping("/alarmes")
public class AlarmeController {
	@Autowired
	private AlarmeService IAlarmeService;
	@Autowired
	private EtiquetaService IEtiquetaService;

	@GetMapping()
	public String listarAlarmes(Model model) {
		model.addAttribute("alarmeList", IAlarmeService.listarAlarmes());
		return "pages/alarmes/list";
	}

	@GetMapping("/{id}")
	public Alarme getAlarmeById(@PathVariable("id") Long id) {
		return IAlarmeService.detalharAlarme(id);
	}

	@GetMapping("/create")
	public String pageAlarmesCreate(Model model) {
		model.addAttribute("alarme", new Alarme());
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/alarmes/create";
	}

	@PostMapping("/save")
	public String salvarAlarme(@ModelAttribute("alarme") @Valid Alarme alarme, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
			return "pages/alarmes/create";
		}
		IAlarmeService.salvarAlarme(alarme);
		return "redirect:/alarmes";
	}

	@GetMapping("/edit/{id}")
	public String atualizarAlarme(@PathVariable("id") Long id, Model model) {
		model.addAttribute("alarme", IAlarmeService.detalharAlarme(id));
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/alarmes/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirAlarme(@PathVariable("id") Long id) {
 		IAlarmeService.excluirAlarme(id);
		return "redirect:/alarmes";
	}
}