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
                        .requestMatchers("/register", "/saveRegister", "/login", "/logout", "/accessDenied").permitAll()

                        // Rotas específicas do ALUNO
                        .requestMatchers("/").hasAnyAuthority("Aluno", "Funcionário", "Admin")
                        .requestMatchers("/minhas-sugestoes", "/sugestoes/create", "/sugestoes/save", "/sugestoes/delete/**").hasAnyAuthority("Aluno", "Funcionário", "Admin")
                        .requestMatchers("/reproduzir").hasAnyAuthority("Aluno", "Funcionário", "Admin")
                        .requestMatchers("/config/**").hasAnyAuthority("Aluno", "Funcionário", "Admin")

                        // Rotas específicas do FUNCIONÁRIO
                        .requestMatchers("/sugestoes/**").hasAnyAuthority("Funcionário", "Admin")
                        .requestMatchers("/musicas/**").hasAnyAuthority("Funcionário", "Admin")
                        .requestMatchers("/historico/**").hasAnyAuthority("Funcionário", "Admin")
                        .requestMatchers("/alarmes/**").hasAnyAuthority("Funcionário", "Admin")
                        .requestMatchers("/etiquetas/**").hasAnyAuthority("Funcionário", "Admin")
                        .requestMatchers("/pausas/**").hasAnyAuthority("Funcionário", "Admin")

                        // ROTAS PARA ADMIN
                        .requestMatchers("/adm/**").hasAuthority("Admin")
                        .requestMatchers("/**").hasAuthority("Admin") // Bloquear o restante

                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .exceptionHandling(handling ->
                        handling.accessDeniedPage("/accessDenied")
                )
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