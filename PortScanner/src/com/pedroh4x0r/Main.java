package com.pedroh4x0r;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String ip = "www.google.com";

        int count = 0; // Count of open ports
        int timeout = 200; // 200 MilliSeconds or 0.2 Seconds

        int[] mostUsedPorts = {20, 21, 22, 23, 25, 53, 80, 8080, 139, 443, 445, 1433, 1434, 3301, 3306, 3389, 5986, 8443, 9002};

            for (int result : mostUsedPorts) {
                // It calls the function PortStatus and set parameters
                if (PortStatus(ip, result, timeout)) { // If true
                    System.out.println("[Open]: " + result);
                    count++;
                }
            }

        // Variable with ternary operator
        String result_count = (count > 0) ? "\n-> [Total Open Ports]: " + count : "[No open ports found]\n-> * Try again or set another address!";
        System.out.println(result_count);
    }

    // Function to check status port
    public static boolean PortStatus (String ip, int port, int timeout) {
        Socket socket = new Socket(); // Instance of Socket
        try {
            socket.connect(new InetSocketAddress(ip, port), timeout); // Open Connection
            return true; // If it receives the answer returns true
        } catch (IOException e) { // Otherwise returns false
            return false;
        }
    }
}
