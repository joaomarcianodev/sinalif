package sinalif.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sinalif.models.PausaProgramada;
import sinalif.services.PausaProgramadaService;

@Controller
@RequestMapping("/pausas")
public class PausaProgramadaController {
	@Autowired
	private PausaProgramadaService IPausaProgramadaService;

	@GetMapping
	public String listarPausasProgramadas(Model model){
		model.addAttribute("pausaList", IPausaProgramadaService.listarPausasProgramadas());
		return "pages/pausas/list";
	}

	@GetMapping("/{id}")
	public PausaProgramada detalharPausaProgramadaById(@PathVariable Long id){
		return IPausaProgramadaService.detalharPausaProgramada(id);
	}

	@GetMapping("/create")
	public String pagePausasCreate(@NotNull Model model) {
		model.addAttribute("pausa", new PausaProgramada());
		return "pages/pausas/create";
	}

	@PostMapping("/save")
	public String salvarPausaProgramada(@ModelAttribute("pausa") @Valid PausaProgramada pausaProgramada, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "pages/pausas/create";
		}
		IPausaProgramadaService.salvarPausaProgramada(pausaProgramada);
		return "redirect:/pausas";
	}

	@GetMapping("/edit/{id}")
	public String atualizarPausaProgramada(@PathVariable Long id, Model model) {
		model.addAttribute("pausa", IPausaProgramadaService.detalharPausaProgramada(id));
		return "pages/pausas/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirPausaProgramada(@PathVariable Long id) {
		IPausaProgramadaService.excluirPausaProgramada(id);
		return "redirect:/pausas";
	}
}