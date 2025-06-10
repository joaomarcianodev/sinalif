package sinalif_srv1.services;

import org.springframework.stereotype.Service;
import sinalif_srv1.dtos.LogReproducaoRecordDto;
import sinalif_srv1.models.LogReproducao;
import sinalif_srv1.repositories.LogReproducaoRepository;
import sinalif_srv1.repositories.MusicaRepository;

import java.util.List;

@Service
public class LogReproducaoService {
    private final LogReproducaoRepository logReproducaoRepository;
    private final MusicaRepository musicaRepository;

    public LogReproducaoService(LogReproducaoRepository logReproducaoRepository, MusicaRepository musicaRepository) {
        this.logReproducaoRepository = logReproducaoRepository;
        this.musicaRepository = musicaRepository;
    }

    public List<LogReproducao> listarLogReproducao(){
        return logReproducaoRepository.findAll();
    }

    public LogReproducao detalharLogReproducao(Long id){
        return logReproducaoRepository.findById(id).get();
    }

    public LogReproducao salvarLogReproducao(LogReproducaoRecordDto logRepDto){
        LogReproducao logRep = new LogReproducao();
        logRep.setId_logReproducao(logRepDto.id_logReproducao());
        logRep.setMusica(musicaRepository.findById(logRepDto.id_musica()).get());
        logRep.setData_reproducao(logRepDto.data_reproducao());

        return logReproducaoRepository.save(logRep);
    }

    public void excluirLogReproducao(Long id){
        logReproducaoRepository.deleteById(id);
    }
}
