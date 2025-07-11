package sinalif.services;

import sinalif.models.Etiqueta;

import java.util.List;

public interface EtiquetaService {
    public List<Etiqueta> getEtiquetas();
    public Etiqueta getEtiqueta(Long id);
    public Etiqueta salvarEtiqueta(Etiqueta etiqueta);
    public Etiqueta atualizarEtiqueta(Long id, Etiqueta etiquetaAtualizada);
    public void excluirEtiqueta(Etiqueta etiqueta);
    public void excluirEtiqueta(Long id);
}
