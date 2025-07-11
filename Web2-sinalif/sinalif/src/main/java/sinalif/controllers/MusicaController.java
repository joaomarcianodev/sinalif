package sinalif.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.dtos.MusicaRecordDto;
import sinalif.models.Alarme;
import sinalif.models.Etiqueta;
import sinalif.models.Musica;
import sinalif.services.MusicaService;

import java.util.List;

@Controller
@RequestMapping("/adm/musicas")
public class MusicaController {
    @Autowired
    private MusicaService IMusicaService;

    @GetMapping
    public String listarMusicas(Model model){
        model.addAttribute("musicaList", IMusicaService.listarMusicas());
        return "pages/adm/musicas/list";
    }

    @GetMapping("/{id}")
    public Musica detalharMusica(@PathVariable Long id){
        return IMusicaService.detalharMusica(id);
    }

    @GetMapping("/create")
    public String pageMusicasCreate(@NotNull Model model) {
        model.addAttribute("musica", new Musica());
        return "pages/adm/musicas/create";
    }

    @PostMapping("/save")
    public String salvarMusica(@ModelAttribute @Valid Musica musica, @NotNull BindingResult result, @NotNull Model model) {
        if (result.hasErrors()) {
            return "pages/adm/musicas/create";
        }
        IMusicaService.salvarMusica(musica);
        return "redirect:/adm/musicas";
    }

    @GetMapping("/edit/{id}")
    public String atualizarMusica(@PathVariable Long id, Model model) {
        model.addAttribute("musica", IMusicaService.detalharMusica(id));
        return "pages/adm/musicas/create";
    }

    @GetMapping("/delete/{id}")
    public String excluirMusica(@PathVariable Long id){
        IMusicaService.excluirMusica(id);
        return "redirect:/adm/musicas";
    }
}
