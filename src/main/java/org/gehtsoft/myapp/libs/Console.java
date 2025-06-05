package org.gehtsoft.myapp.libs;

import java.util.Scanner;

public class Console
{
    private static Console single_instance = null;

    private Scanner scanner;

    private Console()
    {
        scanner = new Scanner(System.in);
    }

    public static synchronized Console getInstance()
    {
        if (single_instance == null)
            single_instance = new Console();

        return single_instance;
    }
    public String readLine() {
        return scanner.nextLine();
    }
    public String readString(String prompt) {
        System.out.print(prompt+": ");
        return scanner.nextLine();
    }
    public int readInteger(String prompt)  {
        System.out.print(prompt+": ");
        return scanner.nextInt();
    }
    public String getOption(String prompt, String[] options) {
        System.out.println(prompt+":");
        for(int i = 0; i<options.length; i++) {
            System.out.println(String.valueOf(i+1) + ". "+options[i]);
        }
        return scanner.nextLine();
    }
}
