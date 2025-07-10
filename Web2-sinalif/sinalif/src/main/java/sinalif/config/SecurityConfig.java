package sinalif.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService uds;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // --- ALTERAÇÃO 1: Desabilitar CSRF ---
        // Necessário para permitir que chamadas POST/PUT internas (como a do WebClient) funcionem sem um token CSRF.
        http.csrf(csrf -> csrf.disable());

        http
                .authorizeHttpRequests(authorize -> authorize
                        // --- ALTERAÇÃO 2: Regras Reorganizadas e Corrigidas ---
                        // A ordem importa: do mais específico para o mais geral.

                        // 1. ROTAS PÚBLICAS E DA API
                        // Rotas que qualquer um pode acessar, incluindo a API interna.
                        .requestMatchers(
                                "/home",
                                "/register",
                                "/saveUser",
                                "/login",
                                "/accessDenied",
                                "/css/**", // Permitir acesso a CSS, JS, etc.
                                "/js/**"
                        ).permitAll()
                        // ESTA É A REGRA-CHAVE: Permite acesso irrestrito a todos os endpoints da API.
                        .requestMatchers("/api/**").permitAll()

                        // 2. ROTAS RESTRITAS POR AUTORIDADE (PARA AS PÁGINAS DE VIEW)
                        // Suas regras de negócio para as páginas que os usuários acessam no navegador.
                        .requestMatchers("/srv1/musicas", "/srv1/musicas/*").hasAnyAuthority("Aluno", "Servidor", "Admin", "Manager")
                        .requestMatchers("/srv1/historico", "/srv1/historico/*").hasAnyAuthority("Aluno", "Servidor", "Admin", "Manager")
                        .requestMatchers("/usuario/**").hasAnyAuthority("Servidor", "Admin")
                        .requestMatchers("/alarmes/list").hasAnyAuthority("Admin", "Manager")
                        .requestMatchers("/alarmes/*").hasAuthority("Admin")

                        // 3. REGRA GERAL
                        // Qualquer outra requisição que não se encaixou nas regras acima deve ser autenticada.
                        .anyRequest().authenticated())

                .formLogin(login ->
                        login.defaultSuccessUrl("/", true))
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
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

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(uds);
        authProvider.setPasswordEncoder(encoder);
        return new ProviderManager(authProvider);
    }
}