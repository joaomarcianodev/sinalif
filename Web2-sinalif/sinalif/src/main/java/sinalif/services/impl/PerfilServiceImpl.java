package sinalif.services.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sinalif.models.Perfil;
import sinalif.repositories.PerfilRepository;
import sinalif.services.PerfilService;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {
	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public List<Perfil> listarPerfis(){
		return perfilRepository.findAll();
	}

	@Override
	public Perfil detalharPerfil(Long id) {
		return perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
	}

	@Override
	public Perfil salvarPerfil(Perfil perfil){
		return perfilRepository.save(perfil);
	}

	@Override
	public Perfil atualizarPerfil(Long id, Perfil perfilAtualizado) {
		Perfil perfilExistente = perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
		perfilExistente.setNome(perfilAtualizado.getNome());
		return perfilRepository.save(perfilExistente);
	}

	@Override
	public void excluirPerfil(Perfil perfil) {
		perfilRepository.deleteById(perfil.getId_perfil());
	}

	@Override
	public void excluirPerfil(Long id) {
		if (perfilRepository.existsById(id)) {
			perfilRepository.deleteById(id);
		} else {
			throw new RuntimeException("Perfil não encontrado com ID: " + id);
		}
	}
}