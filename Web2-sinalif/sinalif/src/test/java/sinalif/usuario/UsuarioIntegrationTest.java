package sinalif.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import sinalif.models.Perfil;
import sinalif.models.Usuario;
import sinalif.repositories.PerfilRepository;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UsuarioIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioService usuarioService;

    private Perfil userRole;
    private Perfil adminRole;

    @BeforeEach
    void setup() {
        adminRole = perfilRepository.findByNome("Admin").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("Admin");
            return perfilRepository.save(p);
        });
        userRole = perfilRepository.findByNome("User").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("User");
            return perfilRepository.save(p);
        });
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve salvar um novo usuário e verificar no banco")
    void testSaveNewUserIntegration() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Integration User");
        novoUsuario.setEmail("integration@iftm.edu.br");
        novoUsuario.setSenha("senhaSegura123");
        novoUsuario.setRoles(Collections.singletonList(userRole));
        mockMvc.perform(post("/adm/usuarios/save")
                        .with(csrf())
                        .flashAttr("usuario", novoUsuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));
        Optional<Usuario> savedUser = usuarioRepository.findUserByEmail("integration@iftm.edu.br");
        assertTrue(savedUser.isPresent());
        assertNotNull(savedUser.get().getId_usuario());
        assertTrue(savedUser.get().getSenha().startsWith("$2a$")); // Verifica se a senha foi encodificada
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve atualizar um usuário existente e verificar no banco")
    void testUpdateExistingUserIntegration() throws Exception {
        Usuario userToUpdate = new Usuario();
        userToUpdate.setNome("Original Name");
        userToUpdate.setEmail("original@iftm.edu.br");
        userToUpdate.setSenha("originalPassword");
        userToUpdate.setRoles(Collections.singletonList(userRole));
        Usuario savedUser = usuarioService.saveUser(userToUpdate); // Salva usando o serviço real

        Usuario updatedDetails = new Usuario();
        updatedDetails.setId_usuario(savedUser.getId_usuario());
        updatedDetails.setNome("Updated Name");
        updatedDetails.setEmail("updated@iftm.edu.br"); // Email pode ser mudado
        updatedDetails.setRoles(Collections.singletonList(adminRole)); // Pode mudar o perfil

        mockMvc.perform(post("/adm/usuarios/saveEdit") // Endpoint de atualização é POST /saveEdit
                        .with(csrf())
                        .flashAttr("usuario", updatedDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));

        // Verifica no banco se as alterações foram persistidas
        Usuario fetchedUser = usuarioRepository.findById(savedUser.getId_usuario()).orElseThrow();
        assertTrue(fetchedUser.getNome().equals("Updated Name"));
        assertTrue(fetchedUser.getEmail().equals("updated@iftm.edu.br"));
        assertTrue(fetchedUser.getRoles().contains(adminRole)); // Verifica se o perfil foi atualizado
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve deletar um usuário e verificar no banco")
    void testDeleteUserIntegration() throws Exception {
        Usuario userToDelete = new Usuario();
        userToDelete.setNome("Delete Me");
        userToDelete.setEmail("delete@iftm.edu.br");
        userToDelete.setSenha("deletethis");
        userToDelete.setRoles(Collections.singletonList(userRole));
        Usuario savedUser = usuarioService.saveUser(userToDelete);

        mockMvc.perform(get("/adm/usuarios/delete/" + savedUser.getId_usuario()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/usuarios"));

        // Verifica se o usuário foi removido do banco
        assertFalse(usuarioRepository.existsById(savedUser.getId_usuario()));
    }

    @Test
    @WithMockUser(username = "register@iftm.edu.br", authorities = {"User"})
    @DisplayName("Integration: Deve registrar um novo usuário via /saveRegister")
    void testRegisterNewUserIntegration() throws Exception {
        Usuario newUser = new Usuario();
        newUser.setNome("New Registered User");
        newUser.setEmail("newuser@iftm.edu.br");
        newUser.setSenha("securePassword123");
        newUser.setRoles(Collections.singletonList(userRole)); // Perfis precisam ser reais

        mockMvc.perform(post("/saveRegister")
                        .with(csrf())
                        .flashAttr("usuario", newUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<Usuario> registeredUser = usuarioRepository.findUserByEmail("newuser@iftm.edu.br");
        assertTrue(registeredUser.isPresent());
        assertTrue(registeredUser.get().getSenha().startsWith("$2a$")); // Senha encodificada
    }
}