package org.gehtsoft.myapp.libs;

import java.util.HashMap;

public class CaesarCipher {
    final String[] alphas = {"abcdefghijklmnopqrstuvwxyz", "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"};

    public String encrypt(String input, int shiftKey) {
        return encryptDecrypt(input, shiftKey, true);
    }

    public String decrypt(String input, int shiftKey) {
        return encryptDecrypt(input, shiftKey, false);
    }

    private String encryptDecrypt(String inputString, int shiftKey, boolean encrypt) {
        HashMap<Character, Character> encodedAlphas = new HashMap<Character, Character>();

        if (!encrypt)
            shiftKey = -shiftKey;

        // Prepare a temporary hash map containing encoded characters (using shiftKey offset) for all alphabets and letter cases variants.
        for (String alpha : alphas) {
            for (String alpha_ : new String[]{alpha, alpha.toUpperCase()}) {
                for (int i = 0; i < alpha_.length(); i++) {
                    // Use formula "(((char + shiftKey) % Length ) + Length ) % Length" to handle negative shiftKeys values
                    encodedAlphas.put(alpha_.charAt(i), alpha_.charAt((((i + shiftKey) % alpha_.length() + alpha_.length()) % alpha_.length())));
                }
            }
        }
        // Build an encoded(decoded) string using our temporary hash map
        StringBuilder result = new StringBuilder();
        for (char curChar : inputString.toCharArray()) {
            if (encodedAlphas.containsKey(curChar))
                curChar = encodedAlphas.get(curChar);
            result.append(curChar);
        }
        return result.toString();
    }
}
