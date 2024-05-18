package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPage extends Application {

    private User currentUser;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/main_page.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Профиль игрока");
            MainPageController mainPageController = loader.getController();
            mainPageController.setUserData(currentUser.getUsername(), currentUser.getEmail());
            mainPageController.initPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(root, 800, 680));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
