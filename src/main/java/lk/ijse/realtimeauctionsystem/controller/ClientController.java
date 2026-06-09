package lk.ijse.realtimeauctionsystem.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.net.Socket;

public class ClientController {
    @FXML private TextArea bidHistory;
    @FXML private TextField bidField;
    @FXML private Label statusLabel;
    private PrintWriter out;
    private Socket socket;

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 6000);
                out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    final String msg = line;
                    Platform.runLater(() -> {
                        if (msg.startsWith("UPDATE")) {
                            String[] d = msg.split(":");
                            statusLabel.setText("Highest: LKR " + d[2]);
                            bidHistory.appendText(d[1] + " bid: LKR " + d[2] + "\n");
                        } else if (msg.startsWith("REJECTED")) {
                            bidHistory.appendText("Alert: " + msg.split(":")[1] + "\n");
                        }
                    });
                }
            } catch (IOException e) { e.printStackTrace(); }
        }).start();
    }

    @FXML
    private void handlePlaceBid() {
        try {
            out.println(bidField.getText());
        } catch (Exception e) { e.printStackTrace(); }
    }
}