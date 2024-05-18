package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainPageController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView game1ImageView;

    @FXML
    private Label game1NameLabel;

    @FXML
    private Button game1PlayButton;

    @FXML
    private ImageView game2ImageView;

    @FXML
    private Label game2NameLabel;

    @FXML
    private Button game2PlayButton;

    @FXML
    private Button logoutButton;

    private Stage primaryStage;
    private String username;
    private String email;



    // Метод для инициализации primaryStage
    public void initPrimaryStage(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }

    public void setUserData(String username, String email) {
        this.username = username;
        this.email = email;
        // Обновляем отображаемые данные
        updateUserData();
    }

    // Метод для обновления отображаемых данных
    private void updateUserData() {
        // Устанавливаем текст для Label из сохраненных данных, если они не пусты
        if (username != null && email != null) {
            usernameLabel.setText("Имя пользователя: " + username);
            emailLabel.setText("Email: " + email);
        }
    }

    // Метод для выхода из аккаунта
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.initPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePlayFirstGame() {
        // Здесь код для обработки нажатия на кнопку первой игры
    }

    @FXML
    private void handlePlaySecondGame() {
        // Здесь код для обработки нажатия на кнопку второй игры
    }

}
