package sinalif_srv3.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sinalif_srv3.dtos.PerfilRecordDto;
import sinalif_srv3.models.PausaProgramada;
import sinalif_srv3.models.Perfil;
import sinalif_srv3.repositories.PerfilRepository;

@Service
public class PerfilService {
	private final PerfilRepository perfilRepository;

	public PerfilService(PerfilRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}

	public List<Perfil> getPerfis(){
		return perfilRepository.findAll();
	}

	public Perfil getPerfil(Long id) {
		return perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
	}

	public Perfil salvarPerfil(PerfilRecordDto perfilRecordDto){
		Perfil perfil = new Perfil();
		perfil.setId_perfil(perfilRecordDto.id_perfil());
		perfil.setNome(perfilRecordDto.nome());

		return perfilRepository.save(perfil);
	}

	public Perfil atualizarPerfil(Long id, PerfilRecordDto perfilRecordDto){
		Perfil perfil = perfilRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
		perfil.setNome(perfilRecordDto.nome());

		return perfilRepository.save(perfil);
	}

	public void excluirPerfil(Long id) {
		if (perfilRepository.existsById(id)) {
			perfilRepository.deleteById(id);
		} else {
			throw new RuntimeException("Perfil não encontrado com ID: " + id);
		}
	}
}