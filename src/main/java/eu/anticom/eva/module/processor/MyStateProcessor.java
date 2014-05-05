package eu.anticom.eva.module.processor;

import java.util.ArrayList;
import java.util.Arrays;

public class MyStateProcessor extends ContainsProcessor {
    protected static final String[] WORDS = {"hi", "hello", "aye", "howdy"};

    public MyStateProcessor() {
        super(new ArrayList<String>(Arrays.asList(WORDS)));
    }

    @Override
    protected String buildMessage(String input) {
        return "hello";
    }
}
