package sinalif.controllers;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.dtos.MusicaRecordDto;
import sinalif.models.*;
import sinalif.repositories.MusicaRepository;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.MusicaService;
import sinalif.services.SugestaoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adm/musicas")
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
    public Musica detalharMusica(@PathVariable Long id){
        return IMusicaService.detalharMusica(id);
    }

    @GetMapping("/create")
    public String pageMusicasCreate(@NotNull Model model) {
        model.addAttribute("musica", new Musica());
        return "pages/musicas/create";
    }

    @PostMapping("/save")
    public String salvarMusica(@ModelAttribute @Valid Musica musica, @NotNull BindingResult result, @NotNull Model model) {
        if (result.hasErrors()) {
            return "pages/musicas/create";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioLogado = usuarioRepository.findUserByEmail(email);

        if (usuarioLogado.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
        } else {
            musica.setUsuario(usuarioLogado.get());
            IMusicaService.salvarMusica(musica);
            return "redirect:/adm/musicas";
        }
    }

    @GetMapping("/save/sugestao/{id}/{status}")
    public String salvarMusicaPorsugestao(@PathVariable Long id, @PathVariable String status) {
        Sugestao sugestao = ISugestaoService.detalharSugestao(id);
        sugestao.setStatus_sugestao(status);

        Musica musica = new Musica();
        musica.setUrl(sugestao.getUrl_sugerida());
        musica.setUsuario(sugestao.getUsuario());

        IMusicaService.salvarMusica(musica);
        String redirectPath = "redirect:/sugestoes/editStatus/"+id+"/"+status;
        return redirectPath;
    }

    @GetMapping("/edit/{id}")
    public String atualizarMusica(@PathVariable Long id, Model model) {
        model.addAttribute("musica", IMusicaService.detalharMusica(id));
        return "pages/musicas/create";
    }

    @GetMapping("/delete/{id}")
    public String excluirMusica(@PathVariable Long id){
        IMusicaService.excluirMusica(id);
        return "redirect:/adm/musicas";
    }

    @GetMapping("/play/{id}")
    public String exibirPlayer(@PathVariable Long id, Model model) {
        Optional<Musica> musicaOptional = musicaRepository.findById(id);

        if (musicaOptional.isPresent()) {
            model.addAttribute("musica", musicaOptional.get());
            return "pages/reprodutor";
        } else {
            return "javascript:alert('ID não encontrado');history.back()";
        }
    }
}
