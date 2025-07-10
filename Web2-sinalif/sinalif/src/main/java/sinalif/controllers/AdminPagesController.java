package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sinalif.models.Alarme;
import sinalif.services.AlarmeService;

import java.util.List;

@Controller
@RequestMapping("/adm")
public class AdminPagesController {
    @Autowired
    private AlarmeService IAlarmeService;
    private final WebClient webClient;

    public AdminPagesController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping()
    public String pageAdmin() {
        return "pages/adm/indexAdmin";
    }

    @GetMapping("/alarmes")
    public String pageAlarmes(Model model) {
        model.addAttribute("alarmeList", IAlarmeService.getAlarmes());
        return "pages/adm/alarmes/list";
    }

    /*@GetMapping("/alarmes")
    public String pageAlarmes(Model model) {
        Flux<Alarme> alarmeFlux = this.webClient.get()
                .uri("/api/alarmes")
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToFlux(Alarme.class);

        model.addAttribute("alarmeList", alarmeFlux.collectList().block());

        return "pages/adm/alarmes/list";
    }*/

    @GetMapping("/alarmes/create")
    public String pageAlarmesCreate(Model model) {
        model.addAttribute("alarme", new Alarme());
        return "pages/adm/alarmes/create";
    }

    /*@PostMapping("alarmes/save")
    public String postMethodName(@ModelAttribute @Valid Alarme alarme, BindingResult result) {
        if (result.hasErrors()) {
            return "alarmes/create";
        }
        IAlarmeService.salvarAlarme(alarme);
        return "redirect:adm/alarmes";
    }*/

    @PostMapping("alarmes/save")
    public String salvarAlarme(@Valid @ModelAttribute("alarme") Alarme alarme, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Erros de validação encontrados!");
            return "pages/adm/alarmes/create";
        }

        System.out.println("Validação passou! Enviando para a API: " + alarme.getDias_semana());

        try {
            this.webClient.post()
                    .uri("/api/alarmes")
                    .bodyValue(alarme)
                    .retrieve()
                    .bodyToMono(Alarme.class)
                    .block(); // Aguarda a conclusão
        } catch (Exception e) {
            model.addAttribute("erroApi", "Houve um problema ao salvar o alarme. Tente novamente.");
            return "pages/adm/alarmes/create";
        }

        return "redirect:/adm/alarmes";
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
