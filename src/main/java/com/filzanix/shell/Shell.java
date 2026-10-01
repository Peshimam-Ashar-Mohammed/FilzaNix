package com.filzanix.shell;

import com.filzanix.filesystem.VirtualFileSystem;
import java.io.IOException;
import java.util.Scanner;

public class Shell {

    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[97m";
    private static final String YELLOW = "\u001B[93m";
    private static final String RED = "\u001B[91m";

    private final Scanner scanner;

    private final VirtualFileSystem fileSystem;

    public Shell() throws IOException {

        this.scanner = new Scanner(System.in);
        this.fileSystem = new VirtualFileSystem();

    }

    public void start() throws IOException{

        while (scanner.hasNextLine()) {

            System.out.print(PURPLE + "filzanix:~$ " + RESET);

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+", 2);

            String command = parts[0];

            String arguments = "";

            if (parts.length > 1) {
                arguments = parts[1];
            }

            if (command.equalsIgnoreCase("help")) {

                System.out.println(WHITE + "Available commands:" + RESET);
                System.out.println(YELLOW + "  help" + WHITE + " - Show this help message" + RESET);
                System.out.println(YELLOW + "  echo" + WHITE + " - Print text to the terminal" + RESET);
                System.out.println(YELLOW + "  exit" + WHITE + " - Exit the program" + RESET);
                System.out.println(YELLOW + "  pwd" + WHITE + " - Print the current working directory" + RESET);
                System.out.println(YELLOW + "  ls" + WHITE + " - List files in the current working directory" + RESET);

            } else if (command.equalsIgnoreCase("echo")) {

                System.out.println(arguments);

            } else if (command.equalsIgnoreCase("exit")) {

                System.out.println(YELLOW + "Shutting down FilzaNix..." + RESET);
                break;

            } else if(command.equalsIgnoreCase("pwd")) {

                System.out.println(fileSystem.getCurrentPath());
            
            } else if(command.equalsIgnoreCase("ls")) {

                fileSystem.listFiles();
            
            }
            else {

                System.out.println(RED + "Command not found: " + command + RESET);

            }
        }

        scanner.close();
    }
}