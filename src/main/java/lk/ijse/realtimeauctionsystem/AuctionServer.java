package lk.ijse.realtimeauctionsystem;

import lk.ijse.realtimeauctionsystem.controller.ClientHandler;
import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionServer {
    private static final int PORT = 6000;
    private static double highestBid = 5000.0;
    private static String highestBidder = "None";
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("[Auction server started - port 6000]");
        System.out.println("Item: Vintage Watch | Starting Price: LKR 5,000");

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            if (sc.nextLine().equalsIgnoreCase("END")) {
                broadcast("CLOSED:" + highestBidder + ":" + highestBid);
                System.out.println("[Auction closed] WINNER: " + highestBidder + " - LKR " + highestBid);
                System.exit(0);
            }
        }).start();

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    public static synchronized void processBid(String user, double bid, ClientHandler sender) {
        if (bid > highestBid) {
            highestBid = bid;
            highestBidder = user;
            System.out.println("BID ACCEPTED - " + user + " : LKR " + bid + " (new highest)");
            broadcast("UPDATE:" + user + ":" + bid);
        } else {
            System.out.println("BID REJECTED - " + user + " : LKR " + bid + " (too low)");
            sender.send("REJECTED:Your bid of LKR " + bid + " is too low. Current: " + highestBid);
        }
    }

    public static void broadcast(String msg) {
        for (ClientHandler c : clients) c.send(msg);
    }
}