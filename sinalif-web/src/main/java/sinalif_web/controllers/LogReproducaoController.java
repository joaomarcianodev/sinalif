package sinalif_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sinalif_web.dtos.LogReproducaoRecordDto;
import sinalif_web.models.LogReproducao;
import sinalif_web.services.LogReproducaoService;

@RestController
@RequestMapping("/srv1/historico")
@CrossOrigin(origins = "*")
public class LogReproducaoController {

    @Autowired
    private LogReproducaoService logReproducaoService;

    @GetMapping
    public List<LogReproducao> listarLogReproducao(){
        return logReproducaoService.listarLogReproducao();
    }

    @GetMapping("/{id}")
    public LogReproducao detalharLogReproducao(@PathVariable Long id){
        return logReproducaoService.detalharLogReproducao(id);
    }

    @PostMapping
    public LogReproducao salvarLogReproducao(@RequestBody LogReproducaoRecordDto logRepDto){
        return logReproducaoService.salvarLogReproducao(logRepDto);
    }

    @DeleteMapping("/{id}")
    public void excluirLogReproducao(@PathVariable Long id){
        logReproducaoService.excluirLogReproducao(id);
    }
}
