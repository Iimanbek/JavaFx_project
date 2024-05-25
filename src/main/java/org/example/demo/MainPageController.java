package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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
            }
        });
    }
    @FXML
    private void handlePlayFirstGame(ActionEvent event) {
        showInstructions("Instructions for Gangster Cleaner: Garbage Mayhem", "Rules:\n" +
                        "\n" +
                        "1.Use the arrows to move the character.\n" +
                        "2.Press \"Space\" or \"Enter\" to collect garbage.\n" +
                        "3.For each collected garbage you get 1 point.\n" +
                        "4.The game ends when 10 pieces of garbage are collected or the field is completed.",
                () -> {
                    try {
                        Stage gameStage = new Stage();

                        new Main().start(gameStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        }

    @FXML
    private void handlePlaySecondGame(ActionEvent event) {
        showInstructions("Instructions for Sort and Save: Ecological Quest", "Rules:\n" +
                        "\n" +
                        "1.Move the trash using the keys or mouse.\n" +
                        "2.Place trash in the correct bins to earn points.\n" +
                        "3.Avoid throwing trash into the wrong bins to avoid losing points.\n" +
                        "4.Complete the sorting in a limited time or number of moves.",
                () -> {
                    try {
                        Stage gameStage = new Stage();

                        new Game().start(gameStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    private void showInstructions(String title, String content, Runnable action) {
        Alert instructionsAlert = new Alert(Alert.AlertType.INFORMATION);
        instructionsAlert.setTitle(title);
        instructionsAlert.setHeaderText(null);
        instructionsAlert.setContentText(content);

        ButtonType okButton = new ButtonType("Ok");
        instructionsAlert.getButtonTypes().setAll(okButton);

        instructionsAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {
                action.run(); // Запуск игры после нажатия кнопки "Ok"
            }
        });
    }


}

