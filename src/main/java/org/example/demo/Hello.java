package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hello extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/hello-view.fxml"));
        Parent root = loader.load();

        // Получаем контроллер
        HelloController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        // Создаем сцену
        Scene scene = new Scene(root);

        // Устанавливаем сцену и показываем окно
        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

