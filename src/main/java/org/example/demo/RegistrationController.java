package org.example.demo;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
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
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

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

    private List<User> users = new ArrayList<>();

    // Метод для чтения данных из файла и их добавления в список пользователей
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) { // Исправляем на 3, так как мы теперь сохраняем email
                    users.add(new User(data[0], data[1], data[2])); // Создаем пользователя с email
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        loadUsersFromFile(); // Вызываем метод загрузки при инициализации контроллера
    }



    public void initPrimaryStage(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleConfirm() {
        // Проверяем, что primaryStage инициализирован
        if (primaryStage == null) {
            System.err.println("Ошибка: primaryStage не инициализирован.");
            return;
        }

        // Создание нового пользователя
        User newUser = new User(usernameField.getText(), emailField.getText(), passwordField.getText());

        users.add(newUser);

        saveUserToFile(newUser);

        try (FileWriter writer = new FileWriter("database.csv", true)) {
            writer.write(newUser.getUsername() + "," + newUser.getEmail() + "," + newUser.getPassword() + "\n");
            errorMessageLabel.setText("Регистрация завершена!");

            // Переход на страницу профиля после успешной регистрации
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/main_page.fxml"));
            Parent root = loader.load();
            MainPageController mainPageController = loader.getController();
            mainPageController.setUserData(newUser.getUsername(), newUser.getEmail());
            mainPageController.initPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (IOException e) {
            e.printStackTrace();
            errorMessageLabel.setText("Ошибка при сохранении данных.");
            return;
        }

        // Очистка полей после регистрации
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
    }


    @FXML
    private void handleRegister() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/registration_form.fxml"));
            Parent root = loader.load();
            RegistrationController registrationFormController = loader.getController();
            registrationFormController.initPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        // Ваша текущая логика проверки и обработки входа пользователя

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/login.fxml"));
            Parent root = loader.load();

            // Получаем контроллер страницы авторизации
            LoginController loginController = loader.getController();

            // Устанавливаем primaryStage и передаем управление LoginController
            loginController.initPrimaryStage(primaryStage);

            // Устанавливаем сцену
            primaryStage.setScene(new Scene(root, 800, 680));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveUserToFile(User user) {
        try (FileWriter writer = new FileWriter("database.csv", true)) {
            writer.write(user.getUsername() + "," + user.getEmail() + "," + user.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
