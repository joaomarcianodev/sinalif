package sinalif_web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sinalif_web.dtos.MusicaRecordDto;
import sinalif_web.models.Musica;
import sinalif_web.repositories.MusicaRepository;

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
        return musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada com ID: " + id));
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
        if (musicaRepository.existsById(id)) {
            musicaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Música não encontrada com ID: " + id);
        }
    }
}
