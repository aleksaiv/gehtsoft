package org.gehtsoft.myapp;

import org.gehtsoft.myapp.task.CaesarCipherTask;
import org.gehtsoft.myapp.task.Calculator;
import org.gehtsoft.myapp.task.Task;
import org.gehtsoft.myapp.libs.Console;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Task task;
        Console console = Console.getInstance();

        while (true) {
            String result = null;
            String option = console.getOption("Please choose an option", new String[]{"Caesar Cipher Encryption", "Caesar Cipher Decryption", "Arithmetic Expression Evaluation", "Exit"});
            switch (option) {
                case "1":
                    task = new CaesarCipherTask(true);
                    result = task.run();
                    break;
                case "2":
                    task = new CaesarCipherTask(false);
                    result = task.run();
                    break;
                case "3":
                    task = new Calculator();
                    result = task.run();
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Incorrect input");
                    continue;
            }
            if(result != null)
                System.out.println("Result: " + result);
        }
    }
}
