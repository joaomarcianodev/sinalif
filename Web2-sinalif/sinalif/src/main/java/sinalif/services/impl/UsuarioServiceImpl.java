package sinalif.services.impl;

import java.util.*;

import lombok.RequiredArgsConstructor;
import sinalif.models.Perfil;
import sinalif.models.Usuario;
import sinalif.repositories.UsuarioRepository;
import sinalif.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarUsers(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario detalharUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    @Override
    public Usuario detalharUsuario(String email) {
        return usuarioRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
    }

    @Override
    public Long saveUser(Usuario usuario) {
        String passwd = usuario.getSenha();
        String encodedPasswod = passwordEncoder.encode(passwd);
        usuario.setSenha(encodedPasswod);
        usuario = usuarioRepository.save(usuario);
        return usuario.getIdUsuario();
    }

    @Override
    public Long saveUserEdit(Usuario usuario) {
        usuario = usuarioRepository.save(usuario);
        return usuario.getIdUsuario();
    }

    @Override
    public Usuario updateUserName(Long userId, String newName) {
        return usuarioRepository.findById(userId) // getIdUsuario é Integer, então converte
                .map(usuario -> {
                    usuario.setNome(newName);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario updateProfilePicture(Long userId, String newPhotoUrl) {
        return usuarioRepository.findById(userId)
                .map(usuario -> {
                    usuario.setUrl_foto_perfil(newPhotoUrl);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario toggleNotifications(Long userId, boolean active) {
        return usuarioRepository.findById(userId)
                .map(usuario -> {
                    usuario.setNotificacoes_ativas(active);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    @Override
    public Usuario changePassword(Long userId, String oldPassword, String newPassword) {
        return usuarioRepository.findById(userId)
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
            List<Perfil> roles = usuario.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (Perfil role : roles) {
                ga.add(role);
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
        if (usuarioRepository.existsById(userId)) {
            usuarioRepository.deleteById(userId);
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + userId);
        }
    }
}