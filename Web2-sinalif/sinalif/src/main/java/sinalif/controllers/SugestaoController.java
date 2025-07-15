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
import sinalif.services.SugestaoService;
import java.util.Optional;

@Controller
@RequestMapping()
public class SugestaoController {
    @Autowired
    private SugestaoService ISugestaoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/sugestoes")
    public String listarSugestoes(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioLogado = usuarioRepository.findUserByEmail(email);

        if (usuarioLogado.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
        } else {
            Usuario usuario = usuarioLogado.get();

            if(usuario.getRoles().get(0).equals("Aluno")){
                model.addAttribute("sugestaoList", ISugestaoService.listarMinhasSugestoes(usuario.getIdUsuario()));
                return "pages/sugestoes/list";
            }else{
                model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
                return "pages/sugestoes/list";
            }
        }


    }

    @GetMapping("/sugestoes/{id}")
    public Sugestao detalharSugestao(@PathVariable Long id) {
        return ISugestaoService.detalharSugestao(id);
    }

    @GetMapping("/sugestoes/usuario/{idUsuario}")
    public String listarSugestoesPorUsuario(@PathVariable Long idUsuario, Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoesPorUsuario(idUsuario));
        return "pages/sugestoes/list";
    }

    @GetMapping("/sugestoes/create")
    public String criarSugestao(Model model) {
        model.addAttribute("sugestao", new Sugestao());
        return "pages/sugestoes/create";
    }

    @PostMapping("/sugestoes/save")
    public String salvarSugestao(@ModelAttribute @Valid Sugestao sugestao, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/sugestoes/create";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioLogado = usuarioRepository.findUserByEmail(email);

        if (usuarioLogado.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
        } else {
            sugestao.setUsuario(usuarioLogado.get());
            ISugestaoService.salvarSugestao(sugestao);
            return "redirect:/sugestoes";
        }
    }

    @GetMapping("/sugestoes/delete/{id}")
    public String excluirSugestao(@PathVariable Long id) {
        ISugestaoService.excluirSugestao(id);
        return "redirect:/sugestoes";
    }

    /*@GetMapping("/reproduzir/{id}")
    public String exibirPlayer(@PathVariable Long id, Model model) {
        Optional<Sugestao> musicaOptional = sugestaoRepository.findById(id);

        if (musicaOptional.isPresent()) {
            model.addAttribute("musica", musicaOptional.get());
            return "pages/reprodutor";
        } else {
            return "javascript:alert('ID não encontrado');history.back()";
        }
    }*/

    @GetMapping("/sugestoes/analise")
    public String listarSugestoesFuncionario(Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
        return "pages/sugestoes/analise";
    }

    @GetMapping("/sugestoes/editStatus/{id}/{status}")
    public String atualizarStatusSugestao(@PathVariable Long id, @PathVariable String status) {
        ISugestaoService.atualizarStatusSugestao(id, status);
        return "redirect:/sugestoes/analise";
    }
}