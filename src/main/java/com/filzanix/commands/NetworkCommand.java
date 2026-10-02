package com.filzanix.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
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


        
    if (command.equalsIgnoreCase("curl")) {
        if (arguments.isBlank()) {
            System.out.println("Usage: curl <URL>");
        } else {
            curl(arguments.trim());
        }
        return true;
    }

    if (command.equalsIgnoreCase("ip") &&
            arguments.trim().equalsIgnoreCase("route")) {
        showRoutes();
        return true;
    }

    if (command.equalsIgnoreCase("netstat")) {
        showNetstat();
        return true;
    }

    if (command.equalsIgnoreCase("traceroute") || command.equalsIgnoreCase("tracert")) {

        if (arguments.isBlank()) {
            System.out.println("Usage: traceroute <hostname>");
        } else {
            traceRoute(arguments.trim());
        }

        return true;
    }

    if (command.equalsIgnoreCase("portscan")) {

        if (arguments.isBlank()) {
            System.out.println("Usage: portscan <hostname>");
        } else {
            portScan(arguments.trim());
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


    private static void curl(String url) {

        try {
            if (!url.startsWith("http://") &&
                    !url.startsWith("https://")) {
                url = "https://" + url;
            }

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("HTTP Status: " + response.statusCode());
            System.out.println("URL: " + response.uri());
            System.out.println("----- RESPONSE BODY -----");
            System.out.println(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Request interrupted.");

        } catch (Exception e) {
            System.out.println("HTTP request failed: " + e.getMessage());
        }
    }


    private static void showRoutes() {

        boolean windows = System.getProperty("os.name")
                .toLowerCase().contains("win");

        if (windows) {
            runSystemCommand("route", "print");
        } else {
            runSystemCommand("ip", "route");
        }
    }

    private static void showNetstat() {

        boolean windows = System.getProperty("os.name")
                .toLowerCase().contains("win");

        if (windows) {
            runSystemCommand("netstat", "-ano");
        } else {
            runSystemCommand("netstat", "-an");
        }
    }

    private static void traceRoute(String hostname) {

        boolean windows = System.getProperty("os.name")
                .toLowerCase().contains("win");

        if (windows) {
            runSystemCommand("tracert", "-d", hostname);
        } else {
            runSystemCommand("traceroute", "-n", hostname);
        }
    }

    private static void runSystemCommand(String... command) {

        try {
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

            String output = new String(
                    process.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            int exitCode = process.waitFor();

            System.out.println(output);

            if (exitCode != 0) {
                System.out.println("Command exited with code: " + exitCode);
            }

        } catch (IOException e) {
            System.out.println("Could not execute command: " + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Command interrupted.");
        }
    }



    private static void portScan(String hostname) {
    
        int[] ports = {
                21, 22, 23, 25, 53, 80, 110, 143,
                443, 445, 3306, 3389, 5432, 8080, 8443
        };

        int openCount = 0;
        int closedCount = 0;
        int filteredCount = 0;
        int errorCount = 0;

        long startTime = System.currentTimeMillis();

        try {
            InetAddress address = InetAddress.getByName(hostname);

            System.out.println();
            System.out.println("======================================");
            System.out.println("       FILZANIX NETWORK SCANNER");
            System.out.println("======================================");
            System.out.println("Target   : " + hostname);
            System.out.println("IP       : " + address.getHostAddress());
            System.out.println("Protocol : TCP");
            System.out.println();

            System.out.printf("%-12s %-12s %s%n",
                    "PORT", "STATE", "SERVICE");

            System.out.println("--------------------------------------");

            for (int port : ports) {
                String state;

                try (Socket socket = new Socket()) {
                    socket.connect(
                            new java.net.InetSocketAddress(address, port),
                            500
                    );

                    state = "OPEN";
                    openCount++;

                } catch (java.net.ConnectException e) {
                state = "CLOSED";
                closedCount++;

            } catch (java.net.SocketTimeoutException e) {
                state = "FILTERED";
                filteredCount++;

            } catch (IOException e) {
                state = "ERROR";
                errorCount++;
            }

                System.out.printf("%-12s %-12s %s%n",
                        port + "/tcp",
                        state,
                        getServiceName(port));
            }

            long duration = System.currentTimeMillis() - startTime;

            System.out.println("--------------------------------------");
            System.out.println("Scan Statistics:");
            System.out.println("Open ports     : " + openCount);
            System.out.println("Closed ports   : " + closedCount);
            System.out.println("Filtered/Other : " + filteredCount);
            System.out.println("Ports scanned  : " + ports.length);
            System.out.println("Duration       : " + duration + " ms");
            System.out.println("Scan completed.");
            System.out.println("Errors         : " + errorCount);

        } catch (UnknownHostException e) {
            System.out.println("Unable to resolve host: " + hostname);
        }
    }

private static String getServiceName(int port) {
   
    return switch (port) {
            case 21 -> "FTP";
            case 22 -> "SSH";
            case 23 -> "Telnet";
            case 25 -> "SMTP";
            case 53 -> "DNS";
            case 80 -> "HTTP";
            case 110 -> "POP3";
            case 143 -> "IMAP";
            case 443 -> "HTTPS";
            case 445 -> "SMB";
            case 3306 -> "MySQL";
            case 3389 -> "RDP";
            case 5432 -> "PostgreSQL";
            case 8080 -> "HTTP-Proxy";
            case 8443 -> "HTTPS-Alt";
            default -> "Unknown";
        };
    }



}