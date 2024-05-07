package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class RegistrationController {

    @FXML
    private VBox vbox;

    @FXML
    private Label errorMessageLabel;

    private Stage primaryStage;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private User currentUser;

    public void initButtons(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        // Загрузка изображения и установка его в ImageView
    }

    @FXML
    private void handleConfirm() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Проверка, если текущий пользователь уже существует
        if (currentUser != null) {
            // Здесь вы можете добавить код для проверки соответствия введенных данных
            // с данными текущего пользователя в базе данных
            if (currentUser.getPassword().equals(password)) {
                // Пароль совпадает - пользователь успешно авторизован
                errorMessageLabel.setText("Вход выполнен!");
            } else {
                errorMessageLabel.setText("Неверный пароль!");
            }
        } else {
            // Создание нового пользователя
            User newUser = new User(username, email, password);
            // Здесь вы можете добавить код для сохранения информации о пользователе в базе данных
            currentUser = newUser;
            errorMessageLabel.setText("Регистрация завершена!");
        }
    }

    @FXML
    private void handleLogin() {
        currentUser = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/login.fxml"));
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        currentUser = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/registration_form.fxml"));
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
