package com.nbugaenco.blockchain.util;

import com.nbugaenco.blockchain.exception.Sha256ProcessException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Utility class containing methods for working with strings and cryptographic operations.
 *
 * @author nbugaenco
 */
public class StringUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Applies the SHA-256 hash function to the input string.
     *
     * @param input the string to hash
     * @return the SHA-256 hashed string
     * @throws Sha256ProcessException if the hashing process fails
     */
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new Sha256ProcessException("SHA256 hashing failed");
        }
    }
}
