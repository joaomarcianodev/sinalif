package sinalif.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sinalif.models.Usuario;
import sinalif.services.PerfilService;
import sinalif.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService IUsuarioService;
    @Autowired
    private PerfilService IPerfilService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/adm/usuarios")
    public String listarUsuarios(Model model){
        model.addAttribute("usuarioList", IUsuarioService.listarUsers());
        return "pages/adm/usuarios/list";
    }

    @GetMapping("/adm/usuarios/delete/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        IUsuarioService.deleteUser(id);
        return "redirect:/adm/usuarios";
    }

    @GetMapping("/adm/usuarios/create")
    public String pageUsuarioCreate(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("perfilList", IPerfilService.listarPerfis());
        return "pages/adm/usuarios/create";
    }

    @GetMapping("/adm/usuarios/edit/{id}")
    public String atualizarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", IUsuarioService.detalharUsuario(id));
        model.addAttribute("perfilList", IPerfilService.listarPerfis());
        return "pages/adm/usuarios/edit";
    }

    @PostMapping("/adm/usuarios/saveEdit")
    public String salvarEdicaoUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result, Model model) {
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty() && !usuario.getEmail().endsWith("iftm.edu.br")) {
            result.rejectValue(
                    "email",
                    "usuario.email.dominioInvalido",
                    "O e-mail deve ser do domínio @iftm.edu.br"
            );
        }

        if (result.hasErrors()) {
            System.out.println("Erros de validação encontrados: " + result.getAllErrors());
            model.addAttribute("perfilList", IPerfilService.listarPerfis());
            return "pages/adm/usuarios/edit";
        }

        Usuario usuarioDoBanco = IUsuarioService.detalharUsuario(usuario.getIdUsuario());

        usuarioDoBanco.setNome(usuario.getNome());
        usuarioDoBanco.setEmail(usuario.getEmail());
        usuarioDoBanco.setRoles(usuario.getRoles());

        Long id = IUsuarioService.saveUserEdit(usuarioDoBanco);
        String message = "Usuário '" + usuario.getNome() + "' registrado com sucesso! ID: " + id;
        model.addAttribute("msg", message);
        return "redirect:/adm/usuarios";
    }

    @PostMapping("/adm/usuarios/save")
    public String salvarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result, Model model) {
        if (usuario.getSenha() != null && usuario.getSenha().length() < 8) {
            result.rejectValue(
                    "senha",
                    "usuario.senha.tamanhoInvalido",
                    "A senha deve ter no mínimo 8 caracteres."
            );
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty() && !usuario.getEmail().endsWith("iftm.edu.br")) {
            result.rejectValue(
                    "email",
                    "usuario.email.dominioInvalido",
                    "O e-mail deve ser do domínio @iftm.edu.br"
            );
        }

        if (result.hasErrors()) {
            System.out.println("Erros de validação encontrados: " + result.getAllErrors());
            model.addAttribute("perfilList", IPerfilService.listarPerfis());
            return "pages/adm/usuarios/create";
        }

        Long id = IUsuarioService.saveUser(usuario);
        String message = "Usuário '" + usuario.getNome() + "' registrado com sucesso!"; // + ID: " + id;
        model.addAttribute("msg", message);
        return "redirect:/adm/usuarios";
    }

    // Rota para a página de registro (Sign-up)
    @GetMapping("/register")
    public String pageRegisterUser(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("perfilList", IPerfilService.listarPerfis());
        return "pages/userAuth/registerUser";
    }

    // Processa o formulário de registro (Sign-up)
    @PostMapping("/saveRegister")
    public String registerUser(@ModelAttribute @Valid Usuario usuario, BindingResult result, Model model) {
        if (usuario.getSenha() != null && usuario.getSenha().length() < 8) {
            result.rejectValue(
                    "senha",
                    "usuario.senha.tamanhoInvalido",
                    "A senha deve ter no mínimo 8 caracteres."
            );
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty() && !usuario.getEmail().endsWith("iftm.edu.br")) {
            result.rejectValue(
                    "email",
                    "usuario.email.dominioInvalido",
                    "O e-mail deve ser do domínio @iftm.edu.br"
            );
        }

        if (result.hasErrors()) {
            System.out.println("Erros de validação encontrados: " + result.getAllErrors());
            model.addAttribute("perfilList", IPerfilService.listarPerfis());
            return "pages/userAuth/registerUser";
        }

        Long id = IUsuarioService.saveUser(usuario);
        String message = "Usuário '" + usuario.getNome() + "' registrado com sucesso! ID: " + id;
        model.addAttribute("msg", message);
        return "redirect:/login"; // Redireciona para a página de login após o registro
    }

    // Rota para a página de login (Sign-in)
    @GetMapping("/login")
    public String showLoginForm() {
        return "pages/userAuth/loginUser";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "pages/userAuth/accessDeniedPage";
    }

    @PutMapping("/usuario/{id}/updateName")
    public String updateUserName(@PathVariable("id") Long userId, @RequestParam("newName") String newName, Model model) {
        try {
            Usuario updatedUsuario = IUsuarioService.updateUserName(userId, newName);
            model.addAttribute("msg", "Nome do usuário " + updatedUsuario.getEmail() + " atualizado para " + newName);
            // Redirecionar para alguma página de sucesso ou perfil do usuário
            return "pages/userAuth/profilePage"; // Crie esta página ou use uma existente
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao atualizar nome do usuário: " + e.getMessage());
            return "pages/errorPage"; // Crie esta página
        }
    }

    @PutMapping("/usuario/{id}/updatePhoto")
    public String updateProfilePhoto(@PathVariable("id") Long userId, @RequestParam("photoUrl") String photoUrl, Model model) {
        try {
            Usuario updatedUsuario = IUsuarioService.updateProfilePicture(userId, photoUrl);
            model.addAttribute("msg", "Foto de perfil do usuário " + updatedUsuario.getEmail() + " atualizada com sucesso.");
            model.addAttribute("usuario", updatedUsuario); // Opcional: passa o usuário atualizado para a view
            return "pages/userAuth/profilePage"; // Ou uma página de sucesso
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao atualizar foto de perfil: " + e.getMessage());
            return "pages/errorPage";
        }
    }

    @PutMapping("/usuario/{id}/notifications")
    public String toggleNotifications(@PathVariable("id") Long userId, @RequestParam("active") boolean active, Model model) {
        try {
            Usuario updatedUsuario = IUsuarioService.toggleNotifications(userId, active);
            String status = active ? "ativadas" : "desativadas";
            model.addAttribute("msg", "Notificações para o usuário " + updatedUsuario.getEmail() + " foram " + status + ".");
            return "pages/userAuth/profilePage"; // Ou uma página de sucesso
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao alterar status de notificações: " + e.getMessage());
            return "pages/errorPage";
        }
    }

    @PostMapping("/usuario/{id}/changePassword")
    public String changeUserPassword(@PathVariable("id") Long userId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Model model) {
        try {
            // Adicionar validação de senha (tamanho mínimo, caracteres especiais, etc.) aqui ou no serviço
            if (newPassword == null || newPassword.length() < 6) { // Exemplo de validação
                throw new RuntimeException("A nova senha deve ter pelo menos 6 caracteres.");
            }

            Usuario updatedUsuario = IUsuarioService.changePassword(userId, oldPassword, newPassword);
            model.addAttribute("msg", "Senha do usuário " + updatedUsuario.getEmail() + " alterada com sucesso.");
            return "pages/userAuth/profilePage"; // Redireciona para a página de perfil ou sucesso
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao alterar senha: " + e.getMessage());
            return "pages/errorPage";
        }
    }

    /*@DeleteMapping("/usuario/{id}/deleteAccount")
    @Transactional // Garante que a operação seja atômica
    public String deleteAccount(@PathVariable("id") Long userId,
                                @AuthenticationPrincipal UserDetails loggedInUser, // Obtém o usuário logado
                                Model model) {
        // Validação de segurança: apenas o próprio usuário ou um Admin pode excluir
        // Supondo que o email do UserDetails é o mesmo email do seu Usuario
        if (loggedInUser == null || !loggedInUser.getUsername().equals(usuarioService.detalharUsuarioPorId(userId).getEmail())) { // Criar detalharUsuarioPorId no service
            if (!loggedInUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
                model.addAttribute("msg", "Você não tem permissão para excluir esta conta.");
                return "pages/userAuth/accessDeniedPage";
            }
        }

        try {
            usuarioService.deleteUser(userId);
            model.addAttribute("msg", "Sua conta foi excluída com sucesso.");
            // Após a exclusão, o usuário provavelmente deve ser deslogado e redirecionado para a home/login
            return "redirect:/logout"; // Redireciona para o endpoint de logout do Spring Security
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao excluir conta: " + e.getMessage());
            return "pages/errorPage";
        }
    }*/
}