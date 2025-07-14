package sinalif.pausaProgramada;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import sinalif.models.PausaProgramada;
import sinalif.repositories.PausaProgramadaRepository;
import sinalif.services.PausaProgramadaService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PausaProgramadaIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PausaProgramadaRepository pausaProgramadaRepository;

    @Autowired
    private PausaProgramadaService pausaProgramadaService;

    @BeforeEach
    void setup() {
        // Limpar o repositório antes de cada teste, se @Transactional não for suficiente
        // pausaProgramadaRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve salvar uma nova pausa programada e verificar no banco")
    void testSaveNewPausaProgramadaIntegration() throws Exception {
        PausaProgramada novaPausa = new PausaProgramada();
        novaPausa.setData_hora_inicio(LocalDateTime.now().plusHours(3));
        novaPausa.setData_hora_fim(LocalDateTime.now().plusHours(4));
        novaPausa.setAtivo(true);
        mockMvc.perform(post("/adm/pausas/save")
                        .with(csrf())
                        .flashAttr("pausa", novaPausa))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/pausas"));
        List<PausaProgramada> pausasNoBanco = pausaProgramadaRepository.findAll();
        assertFalse(pausasNoBanco.isEmpty());
        assertTrue(pausasNoBanco.stream().anyMatch(p -> p.isAtivo() == true && p.getData_hora_inicio().getHour() == novaPausa.getData_hora_inicio().getHour()));
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve atualizar uma pausa programada existente e verificar no banco")
    void testUpdateExistingPausaProgramadaIntegration() throws Exception {
        PausaProgramada pausaExistente = new PausaProgramada();
        pausaExistente.setData_hora_inicio(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        pausaExistente.setData_hora_fim(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        pausaExistente.setAtivo(true);
        PausaProgramada savedPausa = pausaProgramadaService.salvarPausaProgramada(pausaExistente); // Salva usando o serviço real

        PausaProgramada updatedDetails = new PausaProgramada();
        updatedDetails.setId_pausa(savedPausa.getId_pausa()); // Importante para o mapeamento
        updatedDetails.setData_hora_inicio(LocalDateTime.now().plusDays(2).withHour(12).withMinute(0));
        updatedDetails.setData_hora_fim(LocalDateTime.now().plusDays(2).withHour(13).withMinute(0));
        updatedDetails.setAtivo(false);
        // O controlador usa GET /edit/{id} para carregar e POST /save para salvar a edição
        mockMvc.perform(post("/adm/pausas/save")
                        .with(csrf())
                        .flashAttr("pausa", updatedDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/pausas"));
        // Verifica no banco se as alterações foram persistidas
        PausaProgramada fetchedPausa = pausaProgramadaRepository.findById(savedPausa.getId_pausa()).orElseThrow();
        assertTrue(fetchedPausa.isAtivo() == false);
        assertTrue(fetchedPausa.getData_hora_inicio().getHour() == 12);
    }

    @Test
    @WithMockUser(username = "admin@iftm.edu.br", authorities = {"Admin"})
    @DisplayName("Integration: Deve deletar uma pausa programada e verificar no banco")
    void testDeletePausaProgramadaIntegration() throws Exception {
        PausaProgramada pausaToDelete = new PausaProgramada();
        pausaToDelete.setData_hora_inicio(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        pausaToDelete.setData_hora_fim(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        pausaToDelete.setAtivo(true);
        PausaProgramada savedPausa = pausaProgramadaService.salvarPausaProgramada(pausaToDelete);

        mockMvc.perform(get("/adm/pausas/delete/" + savedPausa.getId_pausa()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/pausas"));
        // Verifica se a pausa foi removida do banco
        assertFalse(pausaProgramadaRepository.existsById(savedPausa.getId_pausa()));
    }
}