package lk.ijse.realtimeauctionsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import lk.ijse.realtimeauctionsystem.controller.ClientController;

import java.util.Optional;

public class Launcher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextInputDialog dialog = new TextInputDialog("Amal");
        dialog.setTitle("Auction Login");
        dialog.setHeaderText("Enter your username:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String username = result.get();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Client1.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Auction Client - " + username);

            ClientController controller = loader.getController();
            controller.connect(username, "localhost");

            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}