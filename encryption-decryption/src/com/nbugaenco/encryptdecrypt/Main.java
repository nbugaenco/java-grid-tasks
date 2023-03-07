package com.nbugaenco.encryptdecrypt;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Parameter, String> params = EncoderUtils.getParams(args);

        EncoderFactory encoderFactory = new EncoderFactory();
        Encoder encoder = encoderFactory.create(params.get(Parameter.ALG));

        EncoderUtils.processEncode(params, encoder);
    }
}
