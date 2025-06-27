package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinalif.dtos.MusicaRecordDto;
import sinalif.models.Musica;
import sinalif.repositories.MusicaRepository;
import sinalif.services.MusicaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaServiceImpl implements MusicaService {
    private final MusicaRepository musicaRepository;

    public List<Musica> listarMusicas(){
        return musicaRepository.findAll();
    }

    public Musica detalharMusica(Long id){
        return musicaRepository.findById(id).get();
    }

    public Musica salvarMusica(MusicaRecordDto musicaRecordDto){
        Musica musica = new Musica();
        musica.setId_musica(musicaRecordDto.id_musica());
        musica.setUrl(musicaRecordDto.url());
        musica.setStatus(musicaRecordDto.status());
        musica.setData_criacao(musicaRecordDto.data_criacao());

        return musicaRepository.save(musica);
    }

    public void excluirMusica(Long id){
        musicaRepository.deleteById(id);
    }
}
