package eu.anticom.eva.io;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.event.EventListener;
import eu.anticom.eva.event.OutputEvent;

public class AudioOutput implements Runnable, EventListener {
    protected static final String VOICE_NAME = "kevin16";
    protected Voice voice;

    public AudioOutput() {
        listAllVoices();
    }

    @Override
    public void run() {
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);
        voice.allocate();
        //voice.deallocate();
    }

    @Override
    public void recieveEvent(AbstractEvent event) {
        if(event instanceof OutputEvent) {
            String text = ((OutputEvent) event).getResult();
            voice.speak(text);
        }
//        System.out.println(this.getClass().toString() + " recieved event:");
//        System.out.println(event);
    }

    public void speak(String text) {
        voice.speak(text);
    }

    protected void listAllVoices() {
        System.out.println("--------------------------------------------------");
        System.out.println("All voices available:");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        for (Voice voice : voices) {
            System.out.printf(" - [%s] %s\n", voice.getDomain(), voice.getName());
            System.out.printf("   Description : %s\n", voice.getDescription());
            System.out.printf("   Age         : %s\n", voice.getAge());
            System.out.printf("   Gender      : %s\n", voice.getGender());
            System.out.printf("   Locale      : %s\n", voice.getLocale());
        }
        System.out.println("--------------------------------------------------");
        System.out.println();
    }
}
