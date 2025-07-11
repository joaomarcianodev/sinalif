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
import sinalif.models.PausaProgramada;
import sinalif.models.Perfil;
import sinalif.services.PerfilService;
import sinalif.services.impl.PerfilServiceImpl;

@Controller
@RequestMapping("/adm/perfis")
public class PerfilController {
	@Autowired
	private PerfilService IPerfilService;

	@GetMapping
	public String listarPerfis(Model model){
		model.addAttribute("perfilList", IPerfilService.listarPerfis());
		return "pages/adm/perfis/list";
	}

	@GetMapping("/{id}")
	public Perfil detalharPerfil(@PathVariable Long id){
		return IPerfilService.detalharPerfil(id);
	}

	@GetMapping("/create")
	public String pagePerfisCreate(@NotNull Model model) {
		model.addAttribute("perfil", new Perfil());
		return "pages/adm/perfis/create";
	}

	@PostMapping("/save")
	public String salvarPausaProgramada(@ModelAttribute @Valid Perfil perfil, @NotNull BindingResult result, @NotNull Model model) {
		if (result.hasErrors()) {
			return "pages/adm/perfis/create";
		}
		IPerfilService.salvarPerfil(perfil);
		return "redirect:/adm/perfis";
	}

	@GetMapping("/edit/{id}")
	public String atualizarPerfil(@PathVariable Long id, Model model) {
		model.addAttribute("perfil", IPerfilService.detalharPerfil(id));
		return "pages/adm/perfis/create";
	}

	@GetMapping("/delete/{id}")
	public String excluirPerfil(@PathVariable Long id) {
		IPerfilService.excluirPerfil(id);
		return "redirect:/adm/perfis";
	}
}