package com.filzanix.commands;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkCommand {

    public static boolean execute(String command, String arguments) {

        if (command.equals("ip") && (arguments.isBlank() || arguments.equals("addr"))) {
            showIpAddresses();
            return true;
        }

        return false;
    }

    private static void showIpAddresses() {

        try {
            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();

            System.out.println("========== NETWORK INTERFACES ==========");

            while (interfaces.hasMoreElements()) {

                NetworkInterface networkInterface =
                        interfaces.nextElement();

                System.out.println();
                System.out.println(networkInterface.getName() +
                    " (" + networkInterface.getDisplayName() + ")");
                System.out.println("  Status: " +
                    (networkInterface.isUp() ? "UP" : "DOWN"));

                Enumeration<InetAddress> addresses =
                        networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {

                    InetAddress address = addresses.nextElement();

                        System.out.println("  Address: " + address.getHostAddress());
                }
            }

                    System.out.println("\n========================================");

        } catch (SocketException e) {
            System.out.println("Network error: " + e.getMessage());
        }
    }
}