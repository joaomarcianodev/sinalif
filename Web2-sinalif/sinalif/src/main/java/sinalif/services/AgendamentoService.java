package sinalif.services;

import sinalif.models.Alarme;

public interface AgendamentoService {
    public void verificarEExecutarAlarmes();
    public void iniciarSessao(Alarme alarme);
    public void proximaMusica();
    public void encerrarSessao();
}
