package sinalif.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
public class AdminPagesController {

    @GetMapping()
    public String pageAdmin() {
        return "pages/adm/indexAdmin";
    }

    @GetMapping("/alarmes")
    public String pageAlarmes() {
        return "pages/adm/alarmes";
    }

    @GetMapping("/etiquetas")
    public String pageEtiquetas() {
        return "pages/adm/etiquetas";
    }

    @GetMapping("/historico")
    public String pageHistorico() {
        return "pages/adm/historico";
    }

    @GetMapping("/musicas")
    public String pageMusicas() {
        return "pages/adm/musicas";
    }

    @GetMapping("/pausas")
    public String pagePausas() {
        return "pages/adm/pausas";
    }

    @GetMapping("/perfis")
    public String pagePerfis() {
        return "pages/adm/perfis";
    }

    @GetMapping("/usuarios")
    public String pageUsuarios() {
        return "pages/adm/usuarios";
    }

    @GetMapping("/sugestoes")
    public String pageSugestoes() {
        return "pages/adm/sugestoes";
    }
}
