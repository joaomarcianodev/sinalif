package sinalif.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Importante para segurança baseada em métodos
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService uds;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(requests -> requests
                        // Rotas públicas (permitir a todos)
                        .requestMatchers("/home", "/register", "/saveUser", "/login").permitAll()
                        .requestMatchers("/accessDenied").permitAll() // Página de acesso negado também deve ser pública

                        // Rotas específicas para a visão ALUNO
                        // Exemplo: Alunos podem ver suas músicas e histórico de reprodução
                        .requestMatchers("/srv1/musicas", "/srv1/musicas/*").hasAnyAuthority("Aluno", "Servidor", "Admin") // Aluno pode ver músicas
                        .requestMatchers("/srv1/historico", "/srv1/historico/*").hasAnyAuthority("Aluno", "Servidor", "Admin") // Aluno pode ver histórico
                        // Adicione outras rotas que um aluno deve acessar
                        // Ex: .requestMatchers("/aluno/**").hasAuthority("Aluno")


                        // Rotas específicas para a visão SERVIDOR (e também Admin/Manager, se aplicável)
                        // Exemplo: Servidores (e Admin/Manager) podem gerenciar alarmes, etiquetas, pausas, perfis
                        .requestMatchers("/api/alarmes/**").hasAnyAuthority("Servidor", "Admin")
                        .requestMatchers("/api/etiquetas/**").hasAnyAuthority("Servidor", "Admin")
                        .requestMatchers("/api/pausasProgramadas/**").hasAnyAuthority("Servidor", "Admin")
                        .requestMatchers("/api/perfis/**").hasAnyAuthority("Servidor", "Admin")
                        // Rotas de gerenciamento de usuários, se o servidor tiver permissão para isso
                        .requestMatchers("/usuario/**").hasAnyAuthority("Servidor", "Admin") // Ex: rota de updateUserName

                        // Suas regras existentes
                        .requestMatchers("/heroi/list").hasAnyAuthority("Admin", "Manager")
                        .requestMatchers("/heroi/*").hasAuthority("Admin") // Regra mais restritiva para heroi

                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)) // Redireciona para a raiz após login bem-sucedido
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
                .exceptionHandling(handling ->
                        handling.accessDeniedPage("/accessDenied"))
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(encoder);
        return authenticationProvider;
    }
}