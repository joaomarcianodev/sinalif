package sinalif.controllers;

import sinalif.models.Usuario;
import sinalif.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Go to Registration Page
    /*@GetMapping("/login")
    public String login() {
        return "pages/user/loginUser";
    }*/

    @GetMapping("/register")
    public String register() {
        return "pages/user/registerUser";
    }

    // Read Form data to save into DB
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Usuario usuario, Model model) {
        Integer id = usuarioService.saveUser(usuario);
        String message = "Usuario '" + id + "' saved successfully !";
        model.addAttribute("msg", message);
        return "pages/usuario/registerUser";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "pages/user/accessDeniedPage";
    }
}
