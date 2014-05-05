package eu.anticom.eva.module.io;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventType;

public class AudioOutput extends Module {
    protected boolean running;

    protected Voice voice;

    protected static final String VOICE_NAME = "kevin16";

    @Override
    public void boot() {
        running = true;

        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);
        voice.allocate();

        listAllVoices();
    }

    @Override
    public void shutdown() {
        running = false;
        voice.deallocate();
    }

    @Override
    public void run() {}

    @Override
    public void recieveEvent(Event event) {
        if(event.getEventType() == EventType.OUTPUT) {
            String text = (String) event.getData();
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
