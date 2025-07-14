package sinalif.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sinalif.services.PerfilService;
import sinalif.services.PausaProgramadaService;
import sinalif.services.UsuarioService;

@TestConfiguration
public class TestConfig {
    @Bean
    public UsuarioService usuarioService() {
        return Mockito.mock(UsuarioService.class);
    }

    @Bean
    public PerfilService perfilService() {
        return Mockito.mock(PerfilService.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }
    @Bean
    public PausaProgramadaService pausaProgramadaService() {
        return Mockito.mock(PausaProgramadaService.class);
    }
}