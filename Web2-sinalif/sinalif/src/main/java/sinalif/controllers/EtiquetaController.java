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
import sinalif.models.Etiqueta;
import sinalif.services.EtiquetaService;

@Controller
@RequestMapping("/adm/etiquetas")
public class EtiquetaController {
	@Autowired
	private EtiquetaService IEtiquetaService;

	@GetMapping
	public String listarEtiquetas(Model model){
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/etiquetas/list";
	}

	@GetMapping("/{id}")
	public Etiqueta detalharEtiqueta(@PathVariable Long id){
		return IEtiquetaService.detalharEtiqueta(id);
	}

	@GetMapping("/create")
	public String pageEtiquetasCreate(@NotNull Model model) {
		model.addAttribute("etiqueta", new Etiqueta());
		return "pages/etiquetas/create";
	}

	@PostMapping("/save")
	public String salvarEtiqueta(@ModelAttribute @Valid Etiqueta etiqueta, @NotNull BindingResult result, @NotNull Model model) {
		if (result.hasErrors()) {
			return "pages/etiquetas/create";
		}
		IEtiquetaService.salvarEtiqueta(etiqueta);
		return "redirect:/etiquetas";
	}

	@GetMapping("/edit/{id}")
	public String atualizarEtiqueta(@PathVariable Long id, Model model) {
		model.addAttribute("etiqueta", IEtiquetaService.detalharEtiqueta(id));
		return "pages/etiquetas/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirEtiqueta(@PathVariable Long id) {
		IEtiquetaService.excluirEtiqueta(id);
		return "redirect:/adm/etiquetas";
	}
}