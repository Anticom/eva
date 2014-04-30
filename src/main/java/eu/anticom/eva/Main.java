package eu.anticom.eva;

import eu.anticom.eva.io.AudioOutput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        Eva eva = new Eva();

        System.out.println(eva.getOutputChannels());

        AudioOutput audio = (AudioOutput) eva.getOutputChannels().get("audio");
        System.out.println(audio);
        audio.speak("Booting successful. All Systems are running.");

        /*
        String[] filenames = {
                "src/main/resources/mp3/GetOnTheHorse.mp3",
                "src/main/resources/mp3/TheSunIsUp.mp3"
        };
        for(String filename : filenames) {
            InputStream inputStream = getInputStreamByFilename(filename);
            SpeechResult speechResult = eva.getAudioInput().listenToStream(inputStream);
            System.out.println(speechResult.getHypothesis());
        }
        */
    }

    public static InputStream getInputStreamByFilename(String filename) {
        File inputFile = new File(filename);
        InputStream inputStream = null;
        try {
            inputStream = inputFile.toURI().toURL().openStream();
        } catch (IOException e) {
            System.out.println("Unable to create InputStream from file.");
            e.printStackTrace();
        }

        return inputStream;
    }
}
