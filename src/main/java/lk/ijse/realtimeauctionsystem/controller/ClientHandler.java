package lk.ijse.realtimeauctionsystem.controller;

import lk.ijse.realtimeauctionsystem.AuctionServer;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) { out.println(msg); }

    @Override
    public void run() {
        try {
            username = in.readLine();
            System.out.println("Client connected: " + username);
            String line;
            while ((line = in.readLine()) != null) {
                AuctionServer.processBid(username, Double.parseDouble(line), this);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }
}