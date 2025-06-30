package sinalif.services;

import sinalif.models.Alarme;

import java.util.List;

public interface AlarmeService {
    public List<Alarme> getAlarmes();
    public Alarme getAlarme(Long id);
    public Alarme salvarAlarme(Alarme alarme);
    public void excluirAlarme(Alarme alarme);
    public void excluirAlarme(Long id);
    public Alarme atualizarAlarme(Long id, Alarme alarmeAtualizado);

}
