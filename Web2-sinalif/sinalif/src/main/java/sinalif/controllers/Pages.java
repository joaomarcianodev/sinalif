package sinalif.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
public class Pages {

    @GetMapping()
    public String telaInicial() {
        return "pages/adm/home";
    }

    @GetMapping("/alarmes")
    public String crudAlarmes() {
        return "pages/adm/alarmes";
    }

    @GetMapping("/etiquetas")
    public String crudEtiquetas() {
        return "pages/adm/etiquetas";
    }

    @GetMapping("/historico")
    public String crudHistorico() {
        return "pages/adm/historico";
    }

    @GetMapping("/musicas")
    public String crudMusicas() {
        return "pages/adm/musicas";
    }

    @GetMapping("/pausas")
    public String crudPausas() {
        return "pages/adm/pausas";
    }

    @GetMapping("/perfis")
    public String crudPerfis() {
        return "pages/adm/perfis";
    }

    @GetMapping("/sugestoes")
    public String crudSugestoes() {
        return "pages/adm/sugestoes";
    }

    @GetMapping("/usuarios")
    public String crudUsuarios() {
        return "pages/adm/usuarios";
    }
}
