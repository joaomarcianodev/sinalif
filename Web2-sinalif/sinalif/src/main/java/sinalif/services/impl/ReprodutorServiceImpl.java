package sinalif.services.impl;

import org.springframework.stereotype.Service;
import sinalif.services.ReprodutorService;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Service
public class ReprodutorServiceImpl implements ReprodutorService {

    private Clip currentClip;
    private AudioInputStream currentDecodedStream;
    private AudioInputStream currentInitialStream;
    private volatile boolean manuallyStopped = false;

    public void play(String caminhoArquivo, Runnable onFinishedCallback) {
        stop(); // Para qualquer música que esteja tocando
        manuallyStopped = false;

        try {
            File audioFile = new File(caminhoArquivo);
            currentInitialStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat baseFormat = currentInitialStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(), false
            );
            currentDecodedStream = AudioSystem.getAudioInputStream(decodedFormat, currentInitialStream);

            currentClip = AudioSystem.getClip();

            currentClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    if (!manuallyStopped && onFinishedCallback != null) {
                        onFinishedCallback.run();
                    }
                }
            });

            currentClip.open(currentDecodedStream);
            currentClip.start();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro no ReprodutorService ao tentar tocar " + caminhoArquivo + ": " + e.getMessage());
            stop(); // Limpa os recursos em caso de erro
        }
    }

    /**
     * Para a música que está tocando atualmente e limpa os recursos.
     */
    public void stop() {
        if (currentClip != null) {
            manuallyStopped = true;
            currentClip.stop();
            currentClip.close();
        }
        closeStreams();
        currentClip = null;
    }

    private void closeStreams() {
        try {
            if (currentDecodedStream != null) currentDecodedStream.close();
            if (currentInitialStream != null) currentInitialStream.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar streams de áudio: " + e.getMessage());
        }
    }
}