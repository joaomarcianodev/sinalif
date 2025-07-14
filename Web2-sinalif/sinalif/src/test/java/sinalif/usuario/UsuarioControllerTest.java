package sinalif.usuario;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import sinalif.config.TestConfig;
import sinalif.models.Perfil;
import sinalif.models.Usuario;
import sinalif.services.PerfilService;
import sinalif.services.UsuarioService;

@WebMvcTest(UsuarioController.class)
@Import(TestConfig.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService; // Serviço mockado
    @Autowired
    private PerfilService perfilService;   // Serviço mockado

    @AfterEach
    void resetMocks() {
        reset(usuarioService, perfilService);
    }

    // --- Métodos Auxiliares para criar dados de teste ---
    private List<Usuario> createTestUserList() {
        Perfil adminRole = new Perfil();
        adminRole.setId_perfil(1L);
        adminRole.setNome("Admin");

        Usuario user1 = new Usuario();
        user1.setIdUsuario(1L);
        user1.setNome("Admin Teste");
        user1.setEmail("admin@iftm.edu.br");
        user1.setRoles(List.of(adminRole));

        return List.of(user1);
    }

    private List<Perfil> createTestPerfilList() {
        Perfil adminRole = new Perfil();
        adminRole.setId_perfil(1L);
        adminRole.setNome("Admin");

        Perfil userRole = new Perfil();
        userRole.setId_perfil(2L);
        userRole.setNome("User");

        return List.of(adminRole, userRole);
    }
    // ----------------------------------------------------

    @Test
    @DisplayName("GET /adm/usuarios - Deve redirecionar para login se não autenticado")
    void testListarUsuariosNotAuthenticated() throws Exception {
        mockMvc.perform(get("/adm/usuarios"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login")); // Spring Security redireciona para /login
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("GET /adm/usuarios - Deve exibir 403 Forbidden para usuário sem permissão (User)")
    void testListarUsuariosForbiddenForUserRole() throws Exception {
        when(usuarioService.listarUsers()).thenReturn(createTestUserList());
        mockMvc.perform(get("/adm/usuarios"))
                .andExpect(status().isForbidden()); // Deve retornar 403 Forbidden
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/usuarios - Deve listar usuários para Admin autenticado")
    void testListarUsuariosAuthenticatedAdmin() throws Exception {
        when(usuarioService.listarUsers()).thenReturn(createTestUserList());

        mockMvc.perform(get("/adm/usuarios"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/usuarios/list"))
                .andExpect(model().attributeExists("usuarioList"))
                .andExpect(content().string(containsString("Listagem de Usuários")))
                .andExpect(content().string(containsString("Admin Teste")));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/usuarios/create - Deve exibir formulário de criação para Admin")
    void testPageUsuarioCreateForAdmin() throws Exception {
        when(perfilService.listarPerfis()).thenReturn(createTestPerfilList());

        mockMvc.perform(get("/adm/usuarios/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/usuarios/create"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("perfilList"))
                .andExpect(content().string(containsString("Cadastrar Novo Usuário")));
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("GET /adm/usuarios/create - Deve exibir 403 Forbidden para usuário sem permissão (User)")
    void testPageUsuarioCreateForbiddenForUser() throws Exception {
        mockMvc.perform(get("/adm/usuarios/create"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/usuarios/save - Deve salvar usuário válido com sucesso")
    void testSalvarUsuarioValido() throws Exception {
        Usuario usuarioParaSalvar = new Usuario();
        usuarioParaSalvar.setNome("Novo Usuário");
        usuarioParaSalvar.setEmail("novo@iftm.edu.br");
        usuarioParaSalvar.setSenha("senha123");
        usuarioParaSalvar.setRoles(createTestPerfilList()); // Apenas para simular que roles foram definidas

        // Mock para o serviço de salvar (retorna um ID simulado)
        when(usuarioService.saveUser(any(Usuario.class))).thenReturn(1L);

        mockMvc.perform(post("/adm/usuarios/save")
                        .with(csrf())
                        .flashAttr("usuario", usuarioParaSalvar))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));

        // Verifica se o método saveUser foi chamado
        verify(usuarioService).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/usuarios/save - Deve falhar a validação se senha for curta e retornar para o formulário")
    void testSalvarUsuarioSenhaCurta() throws Exception {
        Usuario usuarioParaSalvar = new Usuario();
        usuarioParaSalvar.setNome("Usuario Invalido");
        usuarioParaSalvar.setEmail("invalido@iftm.edu.br");
        usuarioParaSalvar.setSenha("123"); // Senha muito curta
        usuarioParaSalvar.setRoles(createTestPerfilList());

        when(perfilService.listarPerfis()).thenReturn(createTestPerfilList()); // Para o retorno do formulário

        mockMvc.perform(post("/adm/usuarios/save")
                        .with(csrf())
                        .flashAttr("usuario", usuarioParaSalvar))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/usuarios/create"))
                .andExpect(model().attributeHasFieldErrors("usuario", "senha"))
                .andExpect(content().string(containsString("A senha deve ter no mínimo 8 caracteres.")));

        // Verifica que o serviço de salvar não foi chamado
        verify(usuarioService, never()).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/usuarios/save - Deve falhar a validação se email tiver domínio inválido")
    void testSalvarUsuarioEmailInvalido() throws Exception {
        Usuario usuarioParaSalvar = new Usuario();
        usuarioParaSalvar.setNome("Usuario Email");
        usuarioParaSalvar.setEmail("email@gmail.com"); // Domínio inválido
        usuarioParaSalvar.setSenha("senhavalida123");
        usuarioParaSalvar.setRoles(createTestPerfilList());

        when(perfilService.listarPerfis()).thenReturn(createTestPerfilList());

        mockMvc.perform(post("/adm/usuarios/save")
                        .with(csrf())
                        .flashAttr("usuario", usuarioParaSalvar))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/usuarios/create"))
                .andExpect(model().attributeHasFieldErrors("usuario", "email"))
                .andExpect(content().string(containsString("O e-mail deve ser do domínio @iftm.edu.br")));

        verify(usuarioService, never()).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/usuarios/edit/{id} - Deve exibir formulário de edição para Admin")
    void testAtualizarUsuarioForAdmin() throws Exception {
        Usuario existingUser = new Usuario();
        existingUser.setIdUsuario(1L);
        existingUser.setNome("Existing User");
        existingUser.setEmail("existing@iftm.edu.br");
        existingUser.setRoles(createTestPerfilList());

        when(usuarioService.detalharUsuario(1L)).thenReturn(existingUser);
        when(perfilService.listarPerfis()).thenReturn(createTestPerfilList());

        mockMvc.perform(get("/adm/usuarios/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/usuarios/edit"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("perfilList"))
                .andExpect(content().string(containsString("Editar Usuário")))
                .andExpect(content().string(containsString("Existing User")));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/usuarios/saveEdit - Deve salvar edição de usuário válido com sucesso")
    void testSalvarEdicaoUsuarioValido() throws Exception {
        Usuario existingUser = new Usuario();
        existingUser.setIdUsuario(1L);
        existingUser.setNome("Existing User");
        existingUser.setEmail("existing@iftm.edu.br");
        existingUser.setSenha("oldpassword");
        existingUser.setRoles(createTestPerfilList());

        Usuario updatedUser = new Usuario();
        updatedUser.setIdUsuario(1L);
        updatedUser.setNome("Updated User");
        updatedUser.setEmail("updated@iftm.edu.br");
        updatedUser.setRoles(createTestPerfilList());

        when(usuarioService.detalharUsuario(1L)).thenReturn(existingUser); // Retorna o usuário existente
        when(usuarioService.saveUserEdit(any(Usuario.class))).thenReturn(1L);

        mockMvc.perform(post("/adm/usuarios/saveEdit")
                        .with(csrf())
                        .flashAttr("usuario", updatedUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));

        verify(usuarioService).saveUserEdit(any(Usuario.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/usuarios/delete/{id} - Deve deletar usuário com sucesso e redirecionar")
    void testExcluirUsuario() throws Exception {
        mockMvc.perform(get("/adm/usuarios/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));

        verify(usuarioService).deleteUser(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /register - Deve exibir formulário de registro de usuário")
    void testPageRegisterUser() throws Exception {
        when(perfilService.listarPerfis()).thenReturn(createTestPerfilList());

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/user/registerUser"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("perfilList"))
                .andExpect(content().string(containsString("Registrar Novo Usuário")));
    }

    @Test
    @DisplayName("POST /saveRegister - Deve salvar novo registro de usuário e redirecionar para login")
    void testRegisterUserSuccess() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Registro Teste");
        novoUsuario.setEmail("registro@iftm.edu.br");
        novoUsuario.setSenha("novaSenha123");
        novoUsuario.setRoles(Collections.singletonList(new Perfil(1L, "User"))); // Exemplo de perfil padrão para registro

        when(usuarioService.saveUser(any(Usuario.class))).thenReturn(2L);

        mockMvc.perform(post("/saveRegister")
                        .with(csrf())
                        .flashAttr("usuario", novoUsuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(usuarioService).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("POST /usuario/{id}/updateName - Deve atualizar o nome do usuário")
    void testUpdateUserName() throws Exception {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setIdUsuario(1L);
        updatedUsuario.setNome("Novo Nome");
        updatedUsuario.setEmail("user@iftm.edu.br");

        when(usuarioService.updateUserName(anyLong(), anyString())).thenReturn(updatedUsuario);

        mockMvc.perform(post("/usuario/1/updateName") // POST para simular o PUT em HTML forms
                        .param("newName", "Novo Nome")
                        .with(csrf()))
                .andExpect(status().isOk()) // Pode ser OK se a página de perfil for renderizada
                .andExpect(view().name("pages/user/profilePage"))
                .andExpect(model().attributeExists("msg"));

        verify(usuarioService).updateUserName(1L, "Novo Nome");
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("POST /usuario/{id}/updatePhoto - Deve atualizar a foto de perfil do usuário")
    void testUpdateProfilePhoto() throws Exception {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setIdUsuario(1L);
        updatedUsuario.setUrl_foto_perfil("http://example.com/new_photo.jpg");
        updatedUsuario.setEmail("user@iftm.edu.br");

        when(usuarioService.updateProfilePicture(anyLong(), anyString())).thenReturn(updatedUsuario);

        mockMvc.perform(post("/usuario/1/updatePhoto")
                        .param("photoUrl", "http://example.com/new_photo.jpg")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/user/profilePage"))
                .andExpect(model().attributeExists("msg"));

        verify(usuarioService).updateProfilePicture(1L, "http://example.com/new_photo.jpg");
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("POST /usuario/{id}/notifications - Deve alternar o status das notificações")
    void testToggleNotifications() throws Exception {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setIdUsuario(1L);
        updatedUsuario.setNotificacoes_ativas(true);
        updatedUsuario.setEmail("user@iftm.edu.br");

        when(usuarioService.toggleNotifications(anyLong(), any(Boolean.class))).thenReturn(updatedUsuario);

        mockMvc.perform(post("/usuario/1/notifications")
                        .param("active", "true")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/user/profilePage"))
                .andExpect(model().attributeExists("msg"));

        verify(usuarioService).toggleNotifications(1L, true);
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("POST /usuario/{id}/changePassword - Deve alterar a senha do usuário com sucesso")
    void testChangeUserPasswordSuccess() throws Exception {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setIdUsuario(1L);
        updatedUsuario.setEmail("user@iftm.edu.br");
        updatedUsuario.setSenha("new_encoded_password"); // Senha já encodificada no mock

        when(usuarioService.changePassword(anyLong(), anyString(), anyString())).thenReturn(updatedUsuario);

        mockMvc.perform(post("/usuario/1/changePassword")
                        .param("oldPassword", "old_password")
                        .param("newPassword", "new_password123")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/user/profilePage"))
                .andExpect(model().attributeExists("msg"));

        verify(usuarioService).changePassword(1L, "old_password", "new_password123");
    }

    @Test
    @WithMockUser(username = "user@iftm.edu.br", authorities = {"User"})
    @DisplayName("POST /usuario/{id}/changePassword - Deve falhar ao alterar senha com senha nova muito curta")
    void testChangeUserPasswordShortNewPassword() throws Exception {
        mockMvc.perform(post("/usuario/1/changePassword")
                        .param("oldPassword", "old_password")
                        .param("newPassword", "short") // Senha nova muito curta
                        .with(csrf()))
                .andExpect(status().isOk()) // Pode ser OK se redirecionar para a página de erro com a mensagem
                .andExpect(view().name("pages/errorPage")) // Ou a página de perfil com erro
                .andExpect(model().attributeExists("msg"))
                .andExpect(content().string(containsString("A nova senha deve ter pelo menos 6 caracteres.")));

        verify(usuarioService, never()).changePassword(anyLong(), anyString(), anyString()); // Não deve chamar o serviço
    }

}