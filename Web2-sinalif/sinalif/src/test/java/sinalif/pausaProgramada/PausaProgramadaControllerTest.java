package sinalif.pausaProgramada;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import java.time.LocalDateTime;
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
import sinalif.models.PausaProgramada;
import sinalif.services.PausaProgramadaService;

@WebMvcTest(PausaProgramadaController.class)
@Import(TestConfig.class) // Importa a configuração de testes para os mocks
public class PausaProgramadaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PausaProgramadaService pausaProgramadaService; // Serviço mockado

    @AfterEach
    void resetMocks() {
        reset(pausaProgramadaService);
    }

    // --- Método auxiliar para criar uma lista de Pausas Programadas de teste ---
    private List<PausaProgramada> createTestPausaList() {
        PausaProgramada pausa1 = new PausaProgramada();
        pausa1.setId_pausa(1L);
        pausa1.setData_hora_inicio(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0));
        pausa1.setData_hora_fim(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        pausa1.setAtivo(true);

        PausaProgramada pausa2 = new PausaProgramada();
        pausa2.setId_pausa(2L);
        pausa2.setData_hora_inicio(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0));
        pausa2.setData_hora_fim(LocalDateTime.now().plusDays(2).withHour(11).withMinute(0));
        pausa2.setAtivo(false);

        return List.of(pausa1, pausa2);
    }
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /adm/pausas - Deve redirecionar para login se não autenticado")
    void testListarPausasProgramadasNotAuthenticated() throws Exception {
        mockMvc.perform(get("/adm/pausas"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login")); // Spring Security redireciona para /login
    }

    @Test
    @WithMockUser(username = "user@example.com", authorities = {"User"})
    @DisplayName("GET /adm/pausas - Deve retornar 403 Forbidden para usuário sem permissão (User)")
    void testListarPausasProgramadasForbiddenForUserRole() throws Exception {
        // Mockar o serviço para evitar NullPointerException, mesmo que o acesso seja negado
        when(pausaProgramadaService.listarPausasProgramadas()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/adm/pausas"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/pausas - Deve listar pausas programadas para Admin autenticado")
    void testListarPausasProgramadasAuthenticatedAdmin() throws Exception {
        when(pausaProgramadaService.listarPausasProgramadas()).thenReturn(createTestPausaList());

        mockMvc.perform(get("/adm/pausas"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/pausas/list"))
                .andExpect(model().attributeExists("pausaList"))
                .andExpect(content().string(containsString("Lista de Pausas Programadas")));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/pausas/create - Deve exibir formulário de criação para Admin")
    void testPagePausasCreateForAdmin() throws Exception {
        mockMvc.perform(get("/adm/pausas/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/pausas/create"))
                .andExpect(model().attributeExists("pausa"))
                .andExpect(content().string(containsString("Cadastrar Nova Pausa Programada")));
    }

    @Test
    @WithMockUser(username = "user@example.com", authorities = {"User"})
    @DisplayName("GET /adm/pausas/create - Deve retornar 403 Forbidden para usuário sem permissão (User)")
    void testPagePausasCreateForbiddenForUser() throws Exception {
        mockMvc.perform(get("/adm/pausas/create"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/pausas/save - Deve salvar pausa válida com sucesso")
    void testSalvarPausaProgramadaValida() throws Exception {
        PausaProgramada pausaParaSalvar = new PausaProgramada();
        pausaParaSalvar.setData_hora_inicio(LocalDateTime.now().plusHours(1));
        pausaParaSalvar.setData_hora_fim(LocalDateTime.now().plusHours(2));
        pausaParaSalvar.setAtivo(true);

        when(pausaProgramadaService.salvarPausaProgramada(any(PausaProgramada.class))).thenReturn(pausaParaSalvar);

        mockMvc.perform(post("/adm/pausas/save")
                        .with(csrf())
                        .flashAttr("pausa", pausaParaSalvar))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/pausas"));

        verify(pausaProgramadaService).salvarPausaProgramada(any(PausaProgramada.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/pausas/save - Deve falhar a validação se data de início for nula")
    void testSalvarPausaProgramadaDataInicioNula() throws Exception {
        PausaProgramada pausaInvalida = new PausaProgramada();
        pausaInvalida.setData_hora_inicio(null); // Nulo para causar erro de validação
        pausaInvalida.setData_hora_fim(LocalDateTime.now().plusHours(2));
        pausaInvalida.setAtivo(true);

        mockMvc.perform(post("/adm/pausas/save")
                        .with(csrf())
                        .flashAttr("pausa", pausaInvalida))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/pausas/create"))
                .andExpect(model().attributeHasFieldErrors("pausa", "data_hora_inicio"))
                .andExpect(content().string(containsString("Data de início é um campo obrigatório")));

        verify(pausaProgramadaService, never()).salvarPausaProgramada(any(PausaProgramada.class));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("POST /adm/pausas/save - Deve falhar a validação se data de início for passada")
    void testSalvarPausaProgramadaDataInicioPassada() throws Exception {
        PausaProgramada pausaInvalida = new PausaProgramada();
        pausaInvalida.setData_hora_inicio(LocalDateTime.now().minusDays(1)); // Data no passado
        pausaInvalida.setData_hora_fim(LocalDateTime.now().plusHours(2));
        pausaInvalida.setAtivo(true);

        mockMvc.perform(post("/adm/pausas/save")
                        .with(csrf())
                        .flashAttr("pausa", pausaInvalida))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/pausas/create"))
                .andExpect(model().attributeHasFieldErrors("pausa", "data_hora_inicio"))
                .andExpect(content().string(containsString("A data de início deve ser no presente ou futuro.")));

        verify(pausaProgramadaService, never()).salvarPausaProgramada(any(PausaProgramada.class));
    }


    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/pausas/edit/{id} - Deve exibir formulário de edição para Admin")
    void testAtualizarPausaProgramadaPageForAdmin() throws Exception {
        PausaProgramada pausaExistente = new PausaProgramada();
        pausaExistente.setId_pausa(1L);
        pausaExistente.setData_hora_inicio(LocalDateTime.now().plusDays(5));
        pausaExistente.setData_hora_fim(LocalDateTime.now().plusDays(5).plusHours(2));
        pausaExistente.setAtivo(true);

        when(pausaProgramadaService.detalharPausaProgramada(1L)).thenReturn(pausaExistente);

        mockMvc.perform(get("/adm/pausas/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/adm/pausas/create")) // O controlador redireciona para create na edição
                .andExpect(model().attributeExists("pausa"))
                .andExpect(content().string(containsString("Cadastrar Nova Pausa Programada"))); // Conteúdo da página "create"
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("GET /adm/pausas/delete/{id} - Deve deletar pausa e redirecionar")
    void testExcluirPausaProgramada() throws Exception {
        mockMvc.perform(get("/adm/pausas/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/pausas"));

        verify(pausaProgramadaService).excluirPausaProgramada(1L);
    }
}