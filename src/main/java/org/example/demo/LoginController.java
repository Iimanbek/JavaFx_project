package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

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

    @FXML
    private Label errorMessageLabel;

    private Stage primaryStage;

    private List<User> users = new ArrayList<>();

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


    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    users.add(new User(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        loadUsersFromFile();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            User currentUser = getUserByUsername(username);
            openMainPage(currentUser);
        } else {
            errorMessageLabel.setText("Неверное имя пользователя или email или пароль !");
        }
    }


    private boolean isValidCredentials(String username, String password) {
        loadUsersFromFile(); // Загрузим пользователей из файла перед проверкой

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void openMainPage(User currentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/main_page.fxml"));
            Parent root = loader.load();
            MainPageController mainPageController = loader.getController();
            mainPageController.initPrimaryStage(primaryStage);
            mainPageController.setUserData(currentUser.getUsername(), currentUser.getEmail());
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
