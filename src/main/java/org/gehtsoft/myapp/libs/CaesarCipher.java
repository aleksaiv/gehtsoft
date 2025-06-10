package org.gehtsoft.myapp.libs;

import java.util.*;

public class CaesarCipher {
    final String[] alphas = {"abcdefghijklmnopqrstuvwxyz", "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"};
    final String[] mostUsedAlphas = {"etaoinshrdlcumwfgypbvkjxqz", "оеаинтслвркмдпыубяьгзчйжхшюцэщфёъ"};

    public String encrypt(String input, int shiftKey) {
        return encryptDecrypt(input, shiftKey, true);
    }

    public String decrypt(String input, int shiftKey) {
        return encryptDecrypt(input, shiftKey, false);
    }

    private String encryptDecrypt(String inputString, int shiftKey, boolean encrypt) {
        HashMap<Character, Character> encodedAlphas = new HashMap<>();

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
    /**
     * Trying to guess the shift key based on the most frequently used characters of the English/Russian alphabet.
     *
     * @param inputString Initial encoded string
     * @param charsToGuess How many of the top most frequently used characters to consider
     * @return Possible shift key
     */
    public int findShiftKey(String inputString, int charsToGuess) {

        List<HashMap<Character, Integer>> distributionmaps = new ArrayList<>();
        for (int i = 0; i < alphas.length; i++) {
            distributionmaps.add(new HashMap<>());
        }

        // Calculating char distribution maps for an encoded string
        for (char curChar : inputString.toLowerCase().toCharArray()) {
            for (int i = 0; i < alphas.length; i++) {
                if (alphas[i].indexOf(curChar) != -1)
                    distributionmaps.get(i).merge(curChar, 1, Integer::sum);
            }
        }

        int sum = 0, cnt = 0;
        for (int i = 0; i < distributionmaps.size(); i++) {

            // Sorting the distribution map by values (most frequently used chars)
            List<Map.Entry<Character, Integer>> list =
                    new LinkedList<>(distributionmaps.get(i).entrySet());
            list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
            if (list.isEmpty())
                continue;

            // Calculating an offset based on (encodedChar - mostFrequentlyUsedChar) for all chars in distribution map,
            // sum them up, and then find the average
            int j = 0;
            for (Map.Entry<Character, Integer> aa : list) {
                char charAtJ = mostUsedAlphas[i].charAt(j++);
                sum += aa.getKey() - charAtJ;
                cnt++;
                if (charsToGuess != -1 && j >= charsToGuess)
                    break;
            }

        }
        if (cnt == 0)
            return 0;
        return (int) Math.round((double) sum / cnt);
    }
}
