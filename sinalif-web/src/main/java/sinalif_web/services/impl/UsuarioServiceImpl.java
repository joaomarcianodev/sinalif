package sinalif_web.services.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sinalif_web.models.Perfil;
import sinalif_web.models.Usuario;
import sinalif_web.repositories.UsuarioRepository;
import sinalif_web.services.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Integer saveUsuario(Usuario usuario) {
        String senhaNaoCriptografada = usuario.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaNaoCriptografada);
        usuario.setSenha(senhaCriptografada);

        if (usuario.getData_criacao() == null) {
            usuario.setData_criacao(LocalDate.now());
        }
        
        usuario = usuarioRepo.save(usuario);
        return usuario.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> opt = usuarioRepo.findUsuarioByEmail(email);

        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário com e-mail: " + email + " não encontrado");
        } else {
            Usuario usuario = opt.get();
            List<String> roles = usuario.getRoles();
            Set<GrantedAuthority> authorities = new HashSet<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            springUser = new org.springframework.security.core.userdetails.User(
                    email,
                    usuario.getSenha(),
                    authorities
            );
        }
        return springUser;
    }
}

     

