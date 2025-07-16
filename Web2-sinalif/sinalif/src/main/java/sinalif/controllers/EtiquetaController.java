package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sinalif.models.Etiqueta;
import sinalif.services.EtiquetaService;

@Controller
@RequestMapping("/etiquetas")
public class EtiquetaController {
	@Autowired
	private EtiquetaService IEtiquetaService;

	@GetMapping
	public String listarEtiquetas(Model model){
		model.addAttribute("etiquetaList", IEtiquetaService.listarEtiquetas());
		return "pages/etiquetas/list";
	}

	@GetMapping("/{id}")
	public Etiqueta detalharEtiqueta(@PathVariable("id") Long id){
		return IEtiquetaService.detalharEtiqueta(id);
	}

	@GetMapping("/create")
	public String pageEtiquetasCreate(Model model) {
		model.addAttribute("etiqueta", new Etiqueta());
		return "pages/etiquetas/create";
	}

	@PostMapping("/save")
	public String salvarEtiqueta(@ModelAttribute("etiqueta") @Valid Etiqueta etiqueta, BindingResult result) {
		if (result.hasErrors()) {
			return "pages/etiquetas/create";
		}
		IEtiquetaService.salvarEtiqueta(etiqueta);
		return "redirect:/etiquetas";
	}

	@GetMapping("/edit/{id}")
	public String atualizarEtiqueta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("etiqueta", IEtiquetaService.detalharEtiqueta(id));
		return "pages/etiquetas/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirEtiqueta(@PathVariable("id") Long id) {
		IEtiquetaService.excluirEtiqueta(id);
		return "redirect:/etiquetas";
	}
}