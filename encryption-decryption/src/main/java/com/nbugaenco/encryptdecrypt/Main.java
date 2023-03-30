package com.nbugaenco.encryptdecrypt;

import com.nbugaenco.encryptdecrypt.model.Encoder;
import com.nbugaenco.encryptdecrypt.service.EncoderFactory;
import com.nbugaenco.encryptdecrypt.util.EncoderUtils;
import com.nbugaenco.encryptdecrypt.util.Parameter;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Parameter, String> params = EncoderUtils.getParams(args);

        EncoderFactory encoderFactory = new EncoderFactory();
        Encoder encoder = encoderFactory.create(params.get(Parameter.ALG));

        EncoderUtils.processEncode(params, encoder);
    }
}
