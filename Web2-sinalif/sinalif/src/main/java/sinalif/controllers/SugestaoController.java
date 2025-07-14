package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.models.Sugestao;
import sinalif.models.Usuario;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.MusicaService;
import sinalif.services.SugestaoService;
import sinalif.services.UsuarioService;
import java.util.Optional;

@Controller
@RequestMapping("/sugestoes")
public class SugestaoController {
    @Autowired
    private SugestaoService ISugestaoService;
    @Autowired
    private MusicaService IMusicaService;
    @Autowired
    private UsuarioService IUsuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/adm")
    public String listarSugestoes(Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
        return "pages/adm/sugestoes/list";
    }

    @GetMapping("/adm/{id}")
    public Sugestao detalharSugestao(@PathVariable Long id) {
        return ISugestaoService.detalharSugestao(id);
    }

    @GetMapping("/adm/usuario/{idUsuario}")
    public String listarSugestoesPorUsuario(@PathVariable Long idUsuario, Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoesPorUsuario(idUsuario));
        return "pages/adm/sugestoes/list";
    }

    @GetMapping("/adm/create")
    public String criarSugestao(Model model) {
        model.addAttribute("sugestao", new Sugestao());
        return "pages/adm/sugestoes/create";
    }

    @PostMapping("/adm/save")
    public String salvarSugestao(@ModelAttribute @Valid Sugestao sugestao, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/adm/sugestoes/create";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioLogado = usuarioRepository.findUserByEmail(email);

        if (usuarioLogado.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
        } else {
            sugestao.setUsuario(usuarioLogado.get());
            ISugestaoService.salvarSugestao(sugestao);
            return "redirect:/sugestoes/adm";
        }
    }

    @GetMapping("/adm/delete/{id}")
    public String excluirSugestao(@PathVariable Long id) {
        ISugestaoService.excluirSugestao(id);
        return "redirect:/sugestoes/adm";
    }

    /*@GetMapping("/play/{id}")
    public String exibirPlayer(@PathVariable Long id, Model model) {
        Optional<Sugestao> musicaOptional = sugestaoRepository.findById(id);

        if (musicaOptional.isPresent()) {
            model.addAttribute("musica", musicaOptional.get());
            return "pages/reprodutor";
        } else {
            return "javascript:alert('ID não encontrado');history.back()";
        }
    }*/


    @GetMapping
    public String listarSugestoesFuncionario(Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
        return "pages/sugestoes/pendentes";
    }

    @GetMapping("/editStatus/{id}/{status}")
    public String atualizarStatusSugestao(@PathVariable Long id, @PathVariable String status) {
        ISugestaoService.atualizarStatusSugestao(id, status);
        return "redirect:/sugestoes";
    }
}