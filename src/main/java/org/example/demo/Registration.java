package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Registration extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/registration.fxml"));
        Parent root = loader.load();

        RegistrationController controller = loader.getController();
        controller.initPrimaryStage(primaryStage); // Инициализация primaryStage

        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root, 800, 680));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
