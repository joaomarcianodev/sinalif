package sinalif.controllers;

import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sinalif.models.Usuario;
import sinalif.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
// @RequestMapping("/auth") // Opcional: Se quiser prefixar as rotas de autenticação, por exemplo /auth/register
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota para a página de registro (Sign-up)
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Adiciona um objeto Usuario vazio para o formulário
        return "pages/user/registerUser"; // Caminho para sua view de registro
    }

    // Processa o formulário de registro (Sign-up)
    @PostMapping("/saveUser")
    public String registerUser(@ModelAttribute("usuario") Usuario usuario, Model model) {
        // Validação básica de e-mail (será melhorada no ponto 3)
        if (!usuario.getEmail().endsWith("@iftm.edu.br")) {
            model.addAttribute("msg", "O e-mail deve ser do domínio @iftm.edu.br");
            return "pages/user/registerUser";
        }

        Integer id = usuarioService.saveUser(usuario);
        String message = "Usuário '" + usuario.getNome() + "' registrado com sucesso! ID: " + id;
        model.addAttribute("msg", message);
        return "pages/user/loginUser"; // Redireciona para a página de login após o registro
    }

    @PutMapping("/usuario/{id}/updateName")
    public String updateUserName(@PathVariable("id") Long userId,
                                 @RequestParam("newName") String newName,
                                 Model model) {
        try {
            Usuario updatedUsuario = usuarioService.updateUserName(userId, newName);
            model.addAttribute("msg", "Nome do usuário " + updatedUsuario.getEmail() + " atualizado para " + newName);
            // Redirecionar para alguma página de sucesso ou perfil do usuário
            return "pages/user/profilePage"; // Crie esta página ou use uma existente
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao atualizar nome do usuário: " + e.getMessage());
            return "pages/errorPage"; // Crie esta página
        }
    }

    @PutMapping("/usuario/{id}/updatePhoto")
    public String updateProfilePhoto(@PathVariable("id") Long userId,
                                     @RequestParam("photoUrl") String photoUrl,
                                     Model model) {
        try {
            Usuario updatedUsuario = usuarioService.updateProfilePicture(userId, photoUrl);
            model.addAttribute("msg", "Foto de perfil do usuário " + updatedUsuario.getEmail() + " atualizada com sucesso.");
            model.addAttribute("usuario", updatedUsuario); // Opcional: passa o usuário atualizado para a view
            return "pages/user/profilePage"; // Ou uma página de sucesso
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao atualizar foto de perfil: " + e.getMessage());
            return "pages/errorPage";
        }
    }

    @PutMapping("/usuario/{id}/notifications")
    public String toggleNotifications(@PathVariable("id") Long userId,
                                      @RequestParam("active") boolean active,
                                      Model model) {
        try {
            Usuario updatedUsuario = usuarioService.toggleNotifications(userId, active);
            String status = active ? "ativadas" : "desativadas";
            model.addAttribute("msg", "Notificações para o usuário " + updatedUsuario.getEmail() + " foram " + status + ".");
            return "pages/user/profilePage"; // Ou uma página de sucesso
        } catch (RuntimeException e) {
            model.addAttribute("msg", "Erro ao alterar status de notificações: " + e.getMessage());
            return "pages/errorPage";
        }
    }

    @PostMapping("/usuario/{id}/changePassword")
    public String changeUserPassword(@PathVariable("id") Long userId,
                                     @RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     Model model) {
        try {
            // Adicionar validação de senha (tamanho mínimo, caracteres especiais, etc.) aqui ou no serviço
            if (newPassword == null || newPassword.length() < 6) { // Exemplo de validação
                throw new RuntimeException("A nova senha deve ter pelo menos 6 caracteres.");
            }

            Usuario updatedUsuario = usuarioService.changePassword(userId, oldPassword, newPassword);
            model.addAttribute("msg", "Senha do usuário " + updatedUsuario.getEmail() + " alterada com sucesso.");
            return "pages/user/profilePage"; // Redireciona para a página de perfil ou sucesso
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
                return "pages/user/accessDeniedPage";
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

    // Rota para a página de login (Sign-in) - Gerenciada pelo Spring Security por padrão
    // Não é estritamente necessário ter essa rota no controller, mas pode ser útil para customizar a view
    @GetMapping("/login")
    public String showLoginForm() {
        return "pages/user/loginUser"; // Caminho para sua view de login
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "pages/user/accessDeniedPage";
    }
}