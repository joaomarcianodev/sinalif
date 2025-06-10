package sinalif_srv1.services;

import org.springframework.stereotype.Service;
import sinalif_srv1.dtos.MusicaRecordDto;
import sinalif_srv1.models.Musica;
import sinalif_srv1.repositories.MusicaRepository;

import java.util.List;

@Service
public class MusicaService {
    private final MusicaRepository musicaRepository;

    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

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
