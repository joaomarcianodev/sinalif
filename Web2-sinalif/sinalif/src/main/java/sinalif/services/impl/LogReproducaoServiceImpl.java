package sinalif.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinalif.dtos.LogReproducaoRecordDto;
import sinalif.models.LogReproducao;
import sinalif.repositories.LogReproducaoRepository;
import sinalif.repositories.MusicaRepository;
import sinalif.services.LogReproducaoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogReproducaoServiceImpl implements LogReproducaoService {
    private final LogReproducaoRepository logReproducaoRepository;
    private final MusicaRepository musicaRepository;

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
