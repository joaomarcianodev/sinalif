package sinalif.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sinalif.models.LogReproducao;
import sinalif.services.LogReproducaoService;

@Controller
@RequestMapping("/historico")
public class LogReproducaoController {
    @Autowired
    private LogReproducaoService ILogReproducaoService;

    @GetMapping
    public String listarLogReproducao(Model model){
        model.addAttribute("logList", ILogReproducaoService.listarLogReproducao());
        return "pages/historico/list";
    }

    @GetMapping("/{id}")
    public LogReproducao detalharLogReproducao(@PathVariable Long id){
        return ILogReproducaoService.detalharLogReproducao(id);
    }

    //Fora de contexto ter um Create e Save pra log
    /*@GetMapping("/create")
    public String pageLogsCreate(@NotNull Model model) {
        model.addAttribute("log", new LogReproducao());
        return "pages/historico/create";
    }

    @PostMapping("/save")
    public String salvarLog(@ModelAttribute @Valid LogReproducao log, @NotNull BindingResult result, @NotNull Model model) {
        if (result.hasErrors()) {
            return "pages/historico/create";
        }
        ILogReproducaoService.salvarLogReproducao(log);
        return "redirect:/historico";
    }*/

    @PostMapping
    public LogReproducao salvarLogReproducao(@RequestBody LogReproducao log){
        return ILogReproducaoService.salvarLogReproducao(log);
    }

    //Fora de contexto ter um Update pra log
    /*@GetMapping("/edit/{id}")
    public String atualizarLogReproducao(@PathVariable Long id, Model model) {
        model.addAttribute("historico", ILogReproducaoService.detalharLogReproducao(id));
        return "pages/historico/create";
    }*/

    @GetMapping("/delete/{id}")
    public String excluirLogReproducao(@PathVariable Long id){
        ILogReproducaoService.excluirLogReproducao(id);
        return "redirect:/historico";
    }
}
