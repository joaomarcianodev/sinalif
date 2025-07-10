package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalif.dtos.MusicaRecordDto;
import sinalif.models.Musica;
import sinalif.repositories.MusicaRepository;
import sinalif.services.MusicaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaServiceImpl implements MusicaService {
    @Autowired
    private MusicaRepository musicaRepository;

    @Override
    public List<Musica> listarMusicas(){
        return musicaRepository.findAll();
    }

    @Override
    public Musica detalharMusica(Long id){
        return musicaRepository.findById(id).get();
    }

    @Override
    public Musica salvarMusica(MusicaRecordDto musicaRecordDto){
        Musica musica = new Musica();
        musica.setId_musica(musicaRecordDto.id_musica());
        musica.setUrl(musicaRecordDto.url());
        musica.setStatus(musicaRecordDto.status());
        musica.setData_criacao(musicaRecordDto.data_criacao());

        return musicaRepository.save(musica);
    }

    @Override
    public void excluirMusica(Long id){
        musicaRepository.deleteById(id);
    }
}
