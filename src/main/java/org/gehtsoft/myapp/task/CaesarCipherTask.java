package org.gehtsoft.myapp.task;

import org.gehtsoft.myapp.libs.CaesarCipher;
import org.gehtsoft.myapp.libs.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CaesarCipherTask extends Task {
    CaesarCipher cipher;
    Console console;
    boolean encrypt;
    public CaesarCipherTask(boolean encrypt) {
        cipher = new CaesarCipher();
        console = Console.getInstance();
        this.encrypt = encrypt;
    }
    public String run() throws FileNotFoundException {
        String inputString = "";
        int shiftKey = 0;

        String source = console.getOption("Select source", new String[]{"File", "Console"});
        switch(source){
            case "1":
                String fileName = console.readString("Enter filename");
                Scanner file = new Scanner(new File(fileName));
                inputString = file.nextLine();
                shiftKey = file.nextInt();
                System.out.println("String to " + (encrypt ? "encrypt" : "decrypt") + ": " + inputString);
                System.out.println("Shift key: " + shiftKey);
                break;
            case "2":
                inputString = console.readString("Enter string to " + (encrypt ? "encrypt" : "decrypt"));
                shiftKey =  console.readInteger("Enter shift key");
                break;
        }

        if(encrypt)
            return cipher.encrypt(inputString, shiftKey);
        else
            return cipher.decrypt(inputString, shiftKey);
    }
}
