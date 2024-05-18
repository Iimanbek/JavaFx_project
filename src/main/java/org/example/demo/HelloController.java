package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void startRegistration() {
        try {
            // Создаем новый экземпляр класса Registration и запускаем его
            Registration registration = new Registration();
            registration.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onHelloButtonClick() {

    }

}
