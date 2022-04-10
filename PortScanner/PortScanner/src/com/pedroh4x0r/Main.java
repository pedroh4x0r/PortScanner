package com.pedroh4x0r;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        List<Integer> openPorts = PortStatus("44.228.249.3");
        openPorts.forEach(port -> System.out.println("Port: " + port + "is Open!"));
    }

    // Function to check status port
    public static List<Integer> PortStatus (String ip) {
        ConcurrentLinkedQueue<Integer> openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        AtomicInteger port = new AtomicInteger(0);

        while (port.get() < 65535) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket(); // Instance of Socket
                    socket.connect(new InetSocketAddress(ip, currentPort), 200); // Open Connection
                    socket.close();
                    openPorts.add(currentPort);
                    System.out.println(ip + ": port open -> " + currentPort);
                } catch (IOException e) {
                }
            });
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Integer> openPortList = new ArrayList<>();
        System.out.println("openPortsQueue: " + openPorts.size());
        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }
        return openPortList;
    }
}
