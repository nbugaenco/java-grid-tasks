package com.nbugaenco.encryptdecrypt;

public abstract class Encoder {

    protected String prompt;
    protected int key;

    public Encoder() {
    }

    public abstract String encode();

    public abstract String decode();

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
