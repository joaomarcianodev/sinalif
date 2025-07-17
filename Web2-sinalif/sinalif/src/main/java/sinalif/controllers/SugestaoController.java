package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.models.Musica;
import sinalif.models.Sugestao;
import sinalif.models.Usuario;
import sinalif.services.MusicaService;
import sinalif.services.SugestaoService;
import sinalif.services.UsuarioService;

@Controller
@RequestMapping("/sugestoes")
public class SugestaoController {
    @Autowired
    private SugestaoService ISugestaoService;
    @Autowired
    private MusicaService IMusicaService;
    @Autowired
    private UsuarioService IUsuarioService;

    @GetMapping()
    public String listarSugestoes(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogado = IUsuarioService.detalharUsuario(email);

        if(usuarioLogado.getRoles().get(0).equals("Aluno")){
            model.addAttribute("sugestaoList", ISugestaoService.listarMinhasSugestoes(usuarioLogado.getIdUsuario()));
            return "pages/sugestoes/list";
        }else{
            model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
            return "pages/sugestoes/list";
        }
    }

    @GetMapping("/{id}")
    public Sugestao detalharSugestao(@PathVariable("id") Long id) {
        return ISugestaoService.detalharSugestao(id);
    }

    @GetMapping("/usuario/{id}")
    public String listarSugestoesPorUsuario(@PathVariable("id") Long id, Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoesPorUsuario(id));
        return "pages/sugestoes/list";
    }

    @GetMapping("/create")
    public String criarSugestao(Model model) {
        model.addAttribute("sugestao", new Sugestao());
        return "pages/sugestoes/create";
    }

    @PostMapping("/save")
    public String salvarSugestao(@ModelAttribute("sugestao") @Valid Sugestao sugestao, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/sugestoes/create";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogado = IUsuarioService.detalharUsuario(email);

        sugestao.setUsuario(usuarioLogado);
        ISugestaoService.salvarSugestao(sugestao);
        return "redirect:/sugestoes";
    }

    @GetMapping("/save/musica/{id}/{status}")
    public String salvarMusicaPorsugestao(@PathVariable("id") Long id, @PathVariable String status) {
        Sugestao sugestao = ISugestaoService.detalharSugestao(id);
        sugestao.setStatusSugestao(status);

        Musica musica = new Musica();
        musica.setUrl(sugestao.getUrl());
        musica.setUsuario(sugestao.getUsuario());
        IMusicaService.salvarMusica(musica);

        String redirectPath = "redirect:/sugestoes/editStatus/"+id+"/"+status;
        return redirectPath;
    }

    @GetMapping("/delete/{id}")
    public String excluirSugestao(@PathVariable("id") Long id) {
        ISugestaoService.excluirSugestao(id);
        return "redirect:/sugestoes";
    }

    @GetMapping("/analise")
    public String listarSugestoesFuncionario(Model model) {
        model.addAttribute("sugestaoList", ISugestaoService.listarSugestoes());
        return "pages/sugestoes/analise";
    }

    @GetMapping("/editStatus/{id}/{status}")
    public String atualizarStatusSugestao(@PathVariable("id") Long id, @PathVariable("status") String status) {
        ISugestaoService.atualizarStatusSugestao(id, status);
        return "redirect:/sugestoes/analise";
    }

    @GetMapping("/reproduzir/{id}")
    public String exibirPlayer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("musica", ISugestaoService.detalharSugestao(id));
        return "pages/reprodutor";
    }
}