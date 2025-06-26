package sinalif.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sinalif.models.Usuario;
import sinalif.services.UsuarioService;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService; 

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "usuario/registerUsuario"; 
    }

    @PostMapping("/saveUsuario") 
    public String saveUsuario(
            @ModelAttribute("usuario") Usuario usuario, 
            Model model) {
        Integer id = usuarioService.saveUsuario(usuario); 
        String message = "Usuário '" + id + "' salvo com sucesso!"; 
        model.addAttribute("msg", message);
        model.addAttribute("usuario", new Usuario()); 
        return "usuario/registerUsuario"; 
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "usuario/accessDeniedPage"; // Atualizado para o caminho da view de usuário
    }
}