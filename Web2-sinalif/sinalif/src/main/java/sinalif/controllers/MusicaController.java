package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.models.*;
import sinalif.repositories.MusicaRepository;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.MusicaService;
import sinalif.services.SugestaoService;
import java.util.Optional;

@Controller
@RequestMapping("/musicas")
public class MusicaController {
    @Autowired
    private MusicaService IMusicaService;
    @Autowired
    private SugestaoService ISugestaoService;
    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarMusicas(Model model){
        model.addAttribute("musicaList", IMusicaService.listarMusicas());
        return "pages/musicas/list";
    }

    @GetMapping("/{id}")
    public Musica detalharMusica(@PathVariable("id") Long id){
        return IMusicaService.detalharMusica(id);
    }

    @GetMapping("/create")
    public String pageMusicasCreate(Model model) {
        model.addAttribute("musica", new Musica());
        return "pages/musicas/create";
    }

    @PostMapping("/save")
    public String salvarMusica(@ModelAttribute("musica") @Valid Musica musica, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/musicas/create";
        }

        if(musica.getUsuario() == null || musica.getUsuario().getIdUsuario() == null) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Usuario> usuarioLogado = usuarioRepository.findUserByEmail(email);

            if (usuarioLogado.isEmpty()) {
                throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
            } else {
                musica.setUsuario(usuarioLogado.get());
                IMusicaService.salvarMusica(musica);
                return "redirect:/musicas";
            }
        }else{
            IMusicaService.salvarMusica(musica);
            return "redirect:/musicas";
        }
    }

    @GetMapping("/edit/{id}")
    public String atualizarMusica(@PathVariable("id") Long id, Model model) {
        model.addAttribute("musica", IMusicaService.detalharMusica(id));
        return "pages/musicas/create";
    }

    @GetMapping("/delete/{id}")
    public String excluirMusica(@PathVariable("id") Long id){
        IMusicaService.excluirMusica(id);
        return "redirect:/musicas";
    }

    @GetMapping("/reproduzir/{id}")
    public String exibirPlayer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("musica", IMusicaService.detalharMusica(id));
        return "pages/reprodutor-url";
    }
}
