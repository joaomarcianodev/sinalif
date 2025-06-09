package sinalif_gestor.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SistemasService {
    private RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();;

    private String url_srv1_musicas;
    private String url_srv1_historico;
    private String url_srv2_usuarios;
    private String url_srv2_sugestao;

    public SistemasService(){
        this.restTemplate = new RestTemplate();
        this.url_srv1_musicas = "http://localhost:8082/srv1/musicas";
        this.url_srv1_historico = "http://localhost:8082/srv1/historico";
        this.url_srv2_usuarios = "http://localhost:8083/srv2/usuarios";
        this.url_srv2_sugestao = "http://localhost:8083/srv2/sugestao";
    }

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas
    public ResponseEntity<String> listarMusica(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_musicas, String.class);
        return response;
    }
    public ResponseEntity<String> detalharMusica(Long id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_musicas+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_musicas, musica, String.class);
        return response;
    }
    public ResponseEntity<String> editarMusica(RequestEntity<String> musica, Long id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_musicas+"/"+id, musica, String.class);
        return response;
    }
    public void excluirMusica(Long id){
        restTemplate.delete(url_srv1_musicas+"/"+id);
    }

    //Histórico de Reproduções
    public ResponseEntity<String> listarLogReproducao(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_historico, String.class);
        return response;
    }
    public ResponseEntity<String> detalharLogReproducao(Long id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_historico+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_historico, logRep, String.class);
        return response;
    }
    public ResponseEntity<String> editarLogReproducao(RequestEntity<String> logRep, Long id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_historico+"/"+id, logRep, String.class);
        return response;
    }
    public void excluirLogReproducao(Long id){
        restTemplate.delete(url_srv1_historico+"/"+id);
    }



    /* ******************************************** */
    /* Métodos Servico 2 - API Usuários e Sugestões */
    /* ******************************************** */

    //Usuários
    public ResponseEntity<String> listarUsuario(){
        ResponseEntity<String> responseUsuarios = restTemplate.getForEntity(url_srv2_usuarios, String.class);
        if(!responseUsuarios.getStatusCode().is2xxSuccessful()) return responseUsuarios;

        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(!responseSugestao.getStatusCode().is2xxSuccessful()) return responseSugestao;

        try {
            JsonNode usuarios = mapper.readTree(responseUsuarios.getBody());
            JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());

            List<JsonNode> usuariosList = new ArrayList<>();
            for(JsonNode usuario : usuarios){
                String id_usuario = usuario.get("id_usuario").asText();

                List<JsonNode> sugestoesList = new ArrayList<>();
                for(JsonNode sugestao : sugestoes){
                    if(Objects.equals(sugestao.get("id_usuario").asText(), id_usuario)) sugestoesList.add(sugestao);
                }

                ((ObjectNode) usuario).set("sugestoes", mapper.valueToTree(sugestoesList));
                usuariosList.add(usuario);
            }

            return ResponseEntity.ok(mapper.writeValueAsString(usuariosList));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<String> detalharUsuario(String id){
        ResponseEntity<String> responseUsuario = restTemplate.getForEntity(url_srv2_usuarios+"/"+id, String.class);
        if(!responseUsuario.getStatusCode().is2xxSuccessful()) return responseUsuario;

        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(!responseSugestao.getStatusCode().is2xxSuccessful()) return responseSugestao;

        try {
            JsonNode usuario = mapper.readTree(responseUsuario.getBody());
            JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());
            String id_usuario = usuario.get("id_usuario").asText();

            List<JsonNode> sugestoesList = new ArrayList<>();
            for(JsonNode sugestao : sugestoes){
                if(Objects.equals(sugestao.get("id_usuario").asText(), id_usuario)) sugestoesList.add(sugestao);
            }

            ((ObjectNode) usuario).set("sugestoes", mapper.valueToTree(sugestoesList));
            return ResponseEntity.ok(mapper.writeValueAsString(usuario));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_usuarios, usuario, String.class);
        return response;
    }
    public ResponseEntity<String> editarUsuario(RequestEntity<String> usuario, String id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_usuarios+"/"+id, usuario, String.class);
        return response;
    }
    public void excluirUsuario(String id_usuario){
        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(responseSugestao.getStatusCode().is2xxSuccessful()){
            try {
                JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());
                for(JsonNode sugestao : sugestoes){
                    if(Objects.equals(id_usuario, sugestao.get("id_usuario").asText())){
                        restTemplate.delete(url_srv2_sugestao+"/"+sugestao.get("id_sugestao").asText());
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        restTemplate.delete(url_srv2_usuarios+"/"+id_usuario);
    }

    //Sugestões
    public ResponseEntity<String> listarSugestao(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        return response;
    }
    public ResponseEntity<String> detalharSugestao(String id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_sugestao+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_sugestao, sugestao, String.class);
        return response;
    }
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao, String id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_sugestao+"/"+id, sugestao, String.class);
        return response;
    }
    public void excluirSugestao(String id){
        restTemplate.delete(url_srv2_sugestao+"/"+id);
    }
}
