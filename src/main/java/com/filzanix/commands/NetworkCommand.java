package com.filzanix.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkCommand {

    public static boolean execute(String command, String arguments) {

        if (command.equals("ip") && (arguments.isBlank() || arguments.equals("addr"))) {
            showIpAddresses();
            return true;
        }

        if (command.equalsIgnoreCase("nslookup")) {

            if (arguments.isBlank()) {
                System.out.println("Usage: nslookup <domain>");
            } else {
                lookup(arguments.trim());
            }

            return true;
        }

        if (command.equalsIgnoreCase("ping")) {

            if (arguments.isBlank()) {
                System.out.println("Usage: ping <hostname>");
            } else {
                pingHost(arguments.trim());
            }

            return true;
        }

        return false;
    }
    
    private static void lookup(String hostname) {

        try {
            System.out.println("DNS lookup for: " + hostname);

            InetAddress[] addresses = InetAddress.getAllByName(hostname);

            for (InetAddress address : addresses) {
                System.out.println("Address: " + address.getHostAddress());
            }

        } catch (UnknownHostException e) {
            System.out.println("DNS lookup failed: Unknown host");
        }
    }

    private static void pingHost(String hostname) {

        try {
            InetAddress address = InetAddress.getByName(hostname);

            System.out.println("PING " + hostname +
                    " (" + address.getHostAddress() + ")");

            long start = System.nanoTime();

            boolean reachable = address.isReachable(3000);

            long end = System.nanoTime();

            long timeTaken = (end - start) / 1_000_000;

            if (reachable) {
                System.out.println("Host is reachable");
                System.out.println("Response time: " + timeTaken + " ms");
            } else {
                System.out.println("No response within 3000 ms");
            }

        } catch (UnknownHostException e) {
            System.out.println("Ping failed: Unknown host");

        } catch (IOException e) {
            System.out.println("Network error: " + e.getMessage());
        }
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