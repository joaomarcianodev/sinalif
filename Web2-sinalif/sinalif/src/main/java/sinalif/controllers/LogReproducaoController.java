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
    public LogReproducao detalharLogReproducao(@PathVariable("id") Long id){
        return ILogReproducaoService.detalharLogReproducao(id);
    }

    //Será usado pelo sistema, e não pelos usuários
    @PostMapping
    public LogReproducao salvarLogReproducao(LogReproducao log){
        return ILogReproducaoService.salvarLogReproducao(log);
    }

    @GetMapping("/delete/{id}")
    public String excluirLogReproducao(@PathVariable("id") Long id){
        ILogReproducaoService.excluirLogReproducao(id);
        return "redirect:/historico";
    }

    //Fora de contexto ter uma página de Create para log
    /*@GetMapping("/create")
    public String pageLogsCreate(Model model) {
        model.addAttribute("log", new LogReproducao());
        return "pages/historico/create";
    }*/

    //Fora de contexto ter um save q vem de uma página Create para log
    /*@PostMapping("/save")
    public String salvarLog(@ModelAttribute("log") @Valid LogReproducao log, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/historico/create";
        }
        ILogReproducaoService.salvarLogReproducao(log);
        return "redirect:/historico";
    }*/

    //Fora de contexto ter um Update pra log
    /*@GetMapping("/edit/{id}")
    public String atualizarLogReproducao(@PathVariable Long id, Model model) {
        model.addAttribute("historico", ILogReproducaoService.detalharLogReproducao(id));
        return "pages/historico/create";
    }*/
}
