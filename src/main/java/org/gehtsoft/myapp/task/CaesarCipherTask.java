package org.gehtsoft.myapp.task;

import org.gehtsoft.myapp.libs.CaesarCipher;
import org.gehtsoft.myapp.libs.Console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CaesarCipherTask extends Task {
    CaesarCipher cipher;
    Console console;
    boolean encrypt;
    public CaesarCipherTask(boolean encrypt) {
        cipher = new CaesarCipher();
        console = Console.getInstance();
        this.encrypt = encrypt;
    }
    public String run() throws IOException {
        String inputString = "";
        int shiftKey;

        String source = console.getOption("Select source", new String[]{"File", "Console"});
        switch (source) {
            case "1":
                String fileName = console.readString("Enter filename");
                inputString = new String(Files.readAllBytes(Paths.get(fileName)));
                System.out.println("String to " + (encrypt ? "encrypt" : "decrypt") + ": " + inputString);
                break;
            case "2":
                inputString = console.readString("Enter string to " + (encrypt ? "encrypt" : "decrypt"));
                break;
        }
        if (encrypt) {
            shiftKey = console.readInteger("Enter shift key");
            return cipher.encrypt(inputString, shiftKey);
        }

        String knownKey = console.readString("Do you known the shift key? (y/n)");
        switch (knownKey.toLowerCase()) {
            case "y":
                shiftKey = console.readInteger("Enter shift key");
                break;
            case "n":
                shiftKey = cipher.findShiftKey(inputString, 1);
                System.out.println("Possible shift key: " + shiftKey);
                break;
            default:
                System.out.println("Invalid input. Returning unmodified string.");
                return inputString;
        }
        return cipher.decrypt(inputString, shiftKey);
    }
}
