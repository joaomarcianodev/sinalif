package sinalif.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import sinalif.models.Usuario;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Integer saveUser(Usuario usuario) {
        String passwd = usuario.getSenha();
        String encodedPasswod = passwordEncoder.encode(passwd);
        usuario.setSenha(encodedPasswod);
        usuario = usuarioRepository.save(usuario);
        return usuario.getId_usuario();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> opt = usuarioRepository.findUserByEmail(email);

        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com email: " + email + " não foi encontrado");
        } else {
            Usuario usuario = opt.get();
            List<String> roles = usuario.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (String role : roles) {
                ga.add(new SimpleGrantedAuthority(role));
            }

            springUser = new org.springframework.security.core.userdetails.User(
                    email,
                    usuario.getSenha(),
                    ga);

        }

        return springUser;
    }
}