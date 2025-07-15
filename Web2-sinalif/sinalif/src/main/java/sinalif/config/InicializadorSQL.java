package sinalif.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import sinalif.models.Perfil;
import sinalif.models.Usuario;
import sinalif.repositories.PerfilRepository;
import sinalif.repositories.UsuarioRepository;

import java.util.Collections;

@Component
public class InicializadorSQL implements CommandLineRunner {
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println(">>> BANCO DE DADOS VAZIO. INSERINDO DADOS INICIAIS...");

            Perfil perfilAdmin = new Perfil();
            perfilAdmin.setNome("Admin");
            perfilRepository.save(perfilAdmin);

            Perfil perfilFuncionario = new Perfil();
            perfilFuncionario.setNome("Funcionário");
            perfilRepository.save(perfilFuncionario);

            Perfil perfilALuno = new Perfil();
            perfilALuno.setNome("Aluno");
            perfilRepository.save(perfilALuno);

            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@iftm.edu.br");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setRoles(Collections.singletonList(perfilAdmin));
            usuarioRepository.save(admin);

            System.out.println(">>> DADOS INICIAIS INSERIDOS COM SUCESSO!");
        } else {
            System.out.println(">>> O banco de dados já contém dados.");
        }

        System.out.println(">>> Usuário Inicial: admin@iftm.edu.br");
        System.out.println(">>> Senha Inicial: admin");
    }
}
