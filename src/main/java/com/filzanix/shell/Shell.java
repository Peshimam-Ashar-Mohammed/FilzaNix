package com.filzanix.shell;

import com.filzanix.filesystem.VirtualFileSystem;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;

import java.io.IOException;

public class Shell {

    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[97m";
    private static final String YELLOW = "\u001B[93m";
    private static final String RED = "\u001B[91m";

    private final Terminal terminal;
    private final LineReader lineReader;

    private final List<String> commandHistory = new ArrayList<>();
    private final VirtualFileSystem fileSystem;

    public Shell() throws IOException {

        this.terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        this.lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        this.fileSystem = new VirtualFileSystem();

    }

    public void start() throws IOException{

        while (true) {

            String prompt = PURPLE + "filzanix:"
                    + fileSystem.getCurrentPath() + "$ " + RESET;

            String input;

            try {

                input = lineReader.readLine(prompt).trim();

            } catch (UserInterruptException e) {

                System.out.println();
                continue;

            } catch (EndOfFileException e) {

                break;

            }

            if (input.isEmpty()) {
                continue;
            }

            commandHistory.add(input);

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
                System.out.println(YELLOW + "  mkdir" + WHITE + " - Create a new directory" + RESET);
                System.out.println(YELLOW + "  cd" + WHITE + " - Change the current working directory" + RESET);
                System.out.println(YELLOW + "  touch" + WHITE + " - Create a new file" + RESET);
                System.out.println(YELLOW + "  cat" + WHITE + " - Display the contents of a file" + RESET);
                System.out.println(YELLOW + "  rm <file>" + WHITE + " - Remove a file" + RESET);
                System.out.println(YELLOW + "  rmdir <directory>" + WHITE + " - Remove an empty directory" + RESET);
                System.out.println(YELLOW + "  history" + WHITE + " - Show command history" + RESET);

            } else if (command.equalsIgnoreCase("echo")) {

                boolean append = false;

                if (arguments.contains(">>")) {

                    String[] writeParts = arguments.split(">>", 2);
                    String content = writeParts[0].trim();
                    String filename = writeParts[1].trim();

                    fileSystem.writeFile(filename, content, true);

                } else if (arguments.contains(">")) {

                    String[] writeParts = arguments.split(">", 2);
                    String content = writeParts[0].trim();
                    String filename = writeParts[1].trim();

                    fileSystem.writeFile(filename, content, false);

                } else {

                    System.out.println(arguments);
                }
            } else if (command.equalsIgnoreCase("exit")) {

                System.out.println(YELLOW + "Shutting down FilzaNix..." + RESET);
                break;

            } else if(command.equalsIgnoreCase("pwd")) {

                System.out.println(fileSystem.getCurrentPath());
            
            } else if(command.equalsIgnoreCase("ls")) {

                fileSystem.listFiles();
            
            } else if (command.equalsIgnoreCase("mkdir")) {

                if (arguments.isEmpty()) {

                    System.out.println("Usage: mkdir <directory>");

                } else {

                    fileSystem.makeDirectory(arguments);

                }

            } else if (command.equalsIgnoreCase("cd")) {

                if (arguments.isEmpty()) {

                    fileSystem.changeDirectory("/home");

                } else {

                    fileSystem.changeDirectory(arguments);

                }

            } else if (command.equalsIgnoreCase("touch")) {

                if (arguments.isEmpty()) {

                    System.out.println("Usage: touch <filename>");

                } else {

                    fileSystem.createFile(arguments);

                }

            } else if (command.equalsIgnoreCase("cat")) {

                if (arguments.isEmpty()) {

                    System.out.println("Usage: cat <filename>");

                } else {

                    fileSystem.readFile(arguments);

                }

        } else if (command.equalsIgnoreCase("rm")) {
   
            if (arguments.isBlank()) 
                System.out.println("Usage: rm <filename>");
            else 
                fileSystem.removeFile(arguments.trim());

        }
        else if (command.equalsIgnoreCase("rmdir")) {
            
            if (arguments.isBlank())
                System.out.println("Usage: rmdir <directory>");
            else
                fileSystem.removeDirectory(arguments.trim());
            
        } else if (command.equalsIgnoreCase("history")) {

            for (int i = 0; i < commandHistory.size(); i++) {

                System.out.println(
                        (i + 1) + "  " + commandHistory.get(i)
                );

            }

        }
            else {

                System.out.println(RED + "Command not found: " + command + RESET);

            }
        }

        terminal.close();
    }
}