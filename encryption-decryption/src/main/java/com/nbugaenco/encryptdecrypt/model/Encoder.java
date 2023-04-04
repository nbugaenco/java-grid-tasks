package com.nbugaenco.encryptdecrypt.model;

/**
 * Base class for all encoders in this program
 */
public abstract class Encoder {

    protected String prompt;
    protected int key;

    protected Encoder() {
    }

    public abstract String encode();

    public abstract String decode();

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
