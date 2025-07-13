package sinalif.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sinalif.models.Alarme;
import sinalif.models.Musica;
import sinalif.models.PausaProgramada;
import sinalif.services.PausaProgramadaService;

@Controller
@RequestMapping("/adm/pausas")
public class PausaProgramadaController {
	@Autowired
	private PausaProgramadaService IPausaProgramadaService;

	@GetMapping
	public String listarPausasProgramadas(Model model){
		model.addAttribute("pausaList", IPausaProgramadaService.listarPausasProgramadas());
		return "pages/adm/pausas/list";
	}

	@GetMapping("/{id}")
	public PausaProgramada detalharPausaProgramadaById(@PathVariable Long id){
		return IPausaProgramadaService.detalharPausaProgramada(id);
	}

	@GetMapping("/create")
	public String pagePausasCreate(@NotNull Model model) {
		model.addAttribute("pausa", new PausaProgramada());
		return "pages/adm/pausas/create";
	}

	@PostMapping("/save")
	public String salvarPausaProgramada(@ModelAttribute("pausa") @Valid PausaProgramada pausaProgramada, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "pages/adm/pausas/create";
		}
		IPausaProgramadaService.salvarPausaProgramada(pausaProgramada);
		return "redirect:/adm/pausas";
	}

	@GetMapping("/edit/{id}")
	public String atualizarPausaProgramada(@PathVariable Long id, Model model) {
		model.addAttribute("pausa", IPausaProgramadaService.detalharPausaProgramada(id));
		return "pages/adm/pausas/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirPausaProgramada(@PathVariable Long id) {
		IPausaProgramadaService.excluirPausaProgramada(id);
		return "redirect:/adm/pausas";
	}
}