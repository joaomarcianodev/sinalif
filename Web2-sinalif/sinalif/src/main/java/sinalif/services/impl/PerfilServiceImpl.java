package sinalif.services.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import sinalif.models.Perfil;
import sinalif.repositories.PerfilRepository;
import sinalif.services.PerfilService;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {
	private final PerfilRepository perfilRepository;

	public List<Perfil> getPerfis(){
		return perfilRepository.findAll();
	}

	public Perfil getPerfil(Long id) {
		return perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
	}

	public Perfil salvarPerfil(Perfil perfil){
		return perfilRepository.save(perfil);
	}

	public Perfil atualizarPerfil(Long id, Perfil perfilAtualizado) {
		Perfil perfilExistente = perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
		perfilExistente.setNome(perfilAtualizado.getNome());
		return perfilRepository.save(perfilExistente);
	}

	public void excluirPerfil(Perfil perfil) {
		perfilRepository.deleteById(perfil.getId_perfil());
	}

	public void excluirPerfil(Long id) {
		if (perfilRepository.existsById(id)) {
			perfilRepository.deleteById(id);
		} else {
			throw new RuntimeException("Perfil não encontrado com ID: " + id);
		}
	}
}