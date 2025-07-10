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
    public Usuario updateUserName(Long userId, String newName) {
        return usuarioRepository.findById(userId.intValue()) // getId_usuario é Integer, então converte
                .map(usuario -> {
                    usuario.setNome(newName);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario updateProfilePicture(Long userId, String newPhotoUrl) {
        return usuarioRepository.findById(userId.intValue())
                .map(usuario -> {
                    usuario.setUrl_foto_perfil(newPhotoUrl);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario toggleNotifications(Long userId, boolean active) {
        return usuarioRepository.findById(userId.intValue())
                .map(usuario -> {
                    usuario.setNotificacoes_ativas(active);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario changePassword(Long userId, String oldPassword, String newPassword) {
        return usuarioRepository.findById(userId.intValue())
                .map(usuario -> {
                    // Verifica se a senha antiga corresponde à senha armazenada
                    if (passwordEncoder.matches(oldPassword, usuario.getSenha())) {
                        String encodedNewPassword = passwordEncoder.encode(newPassword);
                        usuario.setSenha(encodedNewPassword);
                        return usuarioRepository.save(usuario);
                    } else {
                        throw new RuntimeException("Senha antiga incorreta.");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
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

    @Override
    public void deleteUser(Long userId) {
        if (usuarioRepository.existsById(userId.intValue())) {
            usuarioRepository.deleteById(userId.intValue());
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + userId);
        }
    }
}