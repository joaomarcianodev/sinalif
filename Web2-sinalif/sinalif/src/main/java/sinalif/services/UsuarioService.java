package sinalif.services;

import sinalif.models.Usuario;

public interface UsuarioService {
    public Integer saveUser(Usuario usuario);
    public Usuario updateUserName(Long userId, String newName);
    public Usuario updateProfilePicture(Long userId, String newPhotoUrl); // Novo metodo
    public Usuario toggleNotifications(Long userId, boolean active);
    public Usuario changePassword(Long userId, String oldPassword, String newPassword); // Novo metodo
    public void deleteUser(Long userId);
}