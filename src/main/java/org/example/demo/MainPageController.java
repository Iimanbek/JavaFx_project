package org.example.demo;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

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
            usernameLabel.setText("Username: " + username);
            emailLabel.setText("Email: " + email);
        }
    }

    // Метод для выхода из аккаунта
    @FXML
    private void handleLogout() {
        Dialog<ButtonType> confirmationDialog = new Dialog<>();
        confirmationDialog.setContentText("Are you sure you want to leave?");
        confirmationDialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Пользователь подтвердил выход
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/login.fxml"));
                    Parent root = loader.load();
                    LoginController loginController = loader.getController();
                    loginController.initPrimaryStage(primaryStage);
                    primaryStage.setScene(new Scene(root, 800, 680));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Пользователь отказался от выхода, ничего не делаем
            }
        });
    }


    @FXML
    private void handlePlayFirstGame(ActionEvent event) {
        // Показать инструкцию для первой игры
        showAlert("Instructions for Gangster Cleaner: Garbage Mayhem", "Rules:\n" +
                "\n" +
                "1.Use the arrows to move the character.\n" +
                "2.Press \"Space\" or \"Enter\" to collect garbage.\n" +
                "3.For each collected garbage you get 1 point.\n" +
                "4.The game ends when 10 pieces of garbage are collected or the field is completed.");
    }

    @FXML
    private void handlePlaySecondGame(ActionEvent event) {
        // Показать инструкцию для второй игры
        showAlert("Instructions for Sort and Save: Ecological Quest", "Rules:\n" +
                "\n" +
                "1.Move the trash using the keys or mouse.\n" +
                "2.Place trash in the correct bins to earn points.\n" +
                "3.Avoid throwing trash into the wrong bins to avoid losing points.\n" +
                "4.Complete the sorting in a limited time or number of moves.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}