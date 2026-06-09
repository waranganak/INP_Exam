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
    @FXML private Label usernameLabel;

    private PrintWriter out;
    private Socket socket;

    public void connect(String username, String ip) {
        usernameLabel.setText("User: " + username);
        new Thread(() -> {
            try {
                socket = new Socket(ip, 6000);
                out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(username);

                String line;
                while ((line = in.readLine()) != null) {
                    final String msg = line;
                    Platform.runLater(() -> {
                        if(msg.startsWith("UPDATE")) {
                            String[] d = msg.split(":");
                            statusLabel.setText("Highest Bid: LKR " + d[2]);
                            bidHistory.appendText(d[1] + " bid LKR " + d[2] + "\n");
                        } else if(msg.startsWith("REJECTED")) {
                            bidHistory.appendText("ERROR: " + msg.split(":")[1] + "\n");
                        } else if(msg.startsWith("CLOSED")) {
                            statusLabel.setText("AUCTION CLOSED! Winner: " + msg.split(":")[1]);
                            bidField.setDisable(true);
                        }
                    });
                }
            } catch (IOException e) {
                Platform.runLater(() -> bidHistory.appendText("Disconnected from server.\n"));
            }
        }).start();
    }

    @FXML
    private void handlePlaceBid() {
        if (out != null && !bidField.getText().isEmpty()) {
            out.println(bidField.getText());
            bidField.clear();
        }
    }

    @FXML
    private void handleDisconnect() {
        try {
            if (socket != null) socket.close();
            Platform.exit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}