package sinalif.services;

public interface ReprodutorService {
    public void play(String caminhoArquivo, Runnable onFinishedCallback);
    public void stop();
}
