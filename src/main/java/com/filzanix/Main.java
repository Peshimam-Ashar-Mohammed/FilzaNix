package com.filzanix;

//import java.util.Scanner;

import com.filzanix.shell.Shell;
import java.io.*;

public class Main {

    // Terminal color definitions

    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[97m";
    private static final String YELLOW = "\u001B[93m";
    private static final String GREEN = "\u001B[92m";

    public static void main(String[] args) throws IOException {

            System.out.println();

            System.out.println(PURPLE +
                    "==================================================" + RESET);

            System.out.println(PURPLE +
                    "                 F I L Z A N I X" + RESET);

            System.out.println(WHITE +
                    "           Virtual Computing Environment" + RESET);

            System.out.println(PURPLE +
                    "==================================================" + RESET);

            System.out.println();

            System.out.println(YELLOW + "[BOOT] " + WHITE +
                    "Initializing environment..." + RESET);

            System.out.println(YELLOW + "[BOOT] " + WHITE +
                    "Loading Java runtime..." + RESET);

            System.out.println(YELLOW + "[BOOT] " + WHITE +
                    "Preparing system..." + RESET);

            System.out.println();

            System.out.println(GREEN + "[ OK ] " + WHITE +
                    "System initialized." + RESET);

            System.out.println();

            System.out.println(PURPLE +
                    "------------------------------------------" + RESET);

            System.out.println(WHITE +
                    "              FILZANIX v0.1" + RESET);

            System.out.println(PURPLE +
                    "------------------------------------------" + RESET);

            System.out.println(WHITE + "Runtime: Java 21" + RESET);

            System.out.println();

            System.out.println(WHITE + "Welcome to FilzaNix! Type 'help' for a list of available commands." + RESET);

            Shell shell = new Shell();

            shell.start();

            
        
    }
}