package org.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double SPEED = 2;

    private Pane root;
    private Truck truck;
    private Trash trash;
    private double dx = SPEED;
    private double dy = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> startGameLoop());

        root.getChildren().add(startButton);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGameLoop() {
        root.getChildren().clear(); // Очищаем корневой узел от кнопки

        truck = new Truck(100, 100);
        trash = createNewTrash();

        root.getChildren().addAll(truck, trash);

        Scene scene = root.getScene();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (dy == 0) {
                        dx = 0;
                        dy = -SPEED;
                    }
                    break;
                case DOWN:
                    if (dy == 0) {
                        dx = 0;
                        dy = SPEED;
                    }
                    break;
                case LEFT:
                    if (dx == 0) {
                        dx = -SPEED;
                        dy = 0;
                    }
                    break;
                case RIGHT:
                    if (dx == 0) {
                        dx = SPEED;
                        dy = 0;
                    }
                    break;
                default:
                    break;
            }
        });

        new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 16_666_666) { // 60 FPS
                    moveTruck();
                    checkCollision();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void moveTruck() {
        double prevX = truck.getX();
        double prevY = truck.getY();

        truck.setX(truck.getX() + dx);
        truck.setY(truck.getY() + dy);

        // Обработка выхода за границы экрана
        if (truck.getX() >= WIDTH) {
            truck.setX(0);
        } else if (truck.getX() < 0) {
            truck.setX(WIDTH);
        }

        if (truck.getY() >= HEIGHT) {
            truck.setY(0);
        } else if (truck.getY() < 0) {
            truck.setY(HEIGHT);
        }
    }

    private void checkCollision() {
        if (truck.getBoundsInParent().intersects(trash.getBoundsInParent())) {
            // Грузовик касается мусора
            root.getChildren().remove(trash); // Удаляем текущий мусор
            trash = createNewTrash();
            root.getChildren().add(trash);
        }
    }

    private Trash createNewTrash() {
        double x = Math.random() * (WIDTH - Trash.RADIUS * 2) + Trash.RADIUS;
        double y = Math.random() * (HEIGHT - Trash.RADIUS * 2) + Trash.RADIUS;
        return new Trash(x, y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
