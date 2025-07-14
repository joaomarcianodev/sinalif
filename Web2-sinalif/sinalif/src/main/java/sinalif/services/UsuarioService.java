package sinalif.services;

import sinalif.models.Perfil;
import sinalif.models.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
    public List<Usuario> listarUsers();
    public Usuario detalharUsuario(Long id);
    public Long saveUser(Usuario usuario);
    public Long saveUserEdit(Usuario usuario);
    public Usuario updateUserName(Long userId, String newName);
    public Usuario updateProfilePicture(Long userId, String newPhotoUrl); // Novo metodo
    public Usuario toggleNotifications(Long userId, boolean active);
    public Usuario changePassword(Long userId, String oldPassword, String newPassword); // Novo metodo
    public void deleteUser(Long userId);
}