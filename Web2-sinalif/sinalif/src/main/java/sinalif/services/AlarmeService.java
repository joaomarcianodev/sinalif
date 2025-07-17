package sinalif.services;

import sinalif.models.Alarme;

import java.util.List;

public interface AlarmeService {
    public List<Alarme> listarAlarmes();
    public Alarme detalharAlarme(Long id);
    public Alarme salvarAlarme(Alarme alarme);
    public void excluirAlarme(Long id);
    public Alarme atualizarAlarme(Long id, Alarme alarmeAtualizado);
    public void excluirAlarme(Alarme alarme);
}
