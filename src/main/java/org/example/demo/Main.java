package org.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Main extends Application {
    private final Map<KeyCode, Boolean> keys = new EnumMap<>(KeyCode.class);
    private final Image playerImage = new Image(getClass().getResource("/1.png").toString());
    private final ImageView playerImageView = new ImageView(playerImage);
    private final Character player = new Character(playerImageView);
    private final Pane root = new Pane();
    private final Random random = new Random();

    private int trashX, trashY;
    private int trashCollected = 0;
    private int totalTrashCollected = 0;
    private int goal = 20;
    private boolean gameOver = false;
    private final int baseX = 5;
    private final int baseY = 5;
    private Text scoreText;
    private ImageView baseImageView;
    private ImageView trashImageView;
    private ImageView treeImageView;
    private final Image treeImage = new Image(getClass().getResource("/derevya.jpg").toString());
    private int treeX, treeY;
    private final Image trashImage = new Image(getClass().getResource("/trash.png").toString());
    private final Image baseImage = new Image(getClass().getResource("/base.jpg").toString());

    public void update() {
        if (!gameOver) {
            if (isPressed(KeyCode.UP)) {
                player.animation.play();
                player.animation.setOffsetY(96);
                player.moveY(-2);
            } else if (isPressed(KeyCode.DOWN)) {
                player.animation.play();
                player.animation.setOffsetY(0);
                player.moveY(2);
            } else if (isPressed(KeyCode.RIGHT)) {
                player.animation.play();
                player.animation.setOffsetY(64);
                player.moveX(2);
            } else if (isPressed(KeyCode.LEFT)) {
                player.animation.play();
                player.animation.setOffsetY(32);
                player.moveX(-2);
            } else {
                player.animation.stop();
            }

            // Проверка на сбор мусора
            if (player.getBoundsInParent().intersects(trashImageView.getBoundsInParent())) {
                if (trashCollected < 5) {
                    trashCollected++;
                    totalTrashCollected++;
                    root.getChildren().remove(trashImageView);
                    generateTrash();
                    updateScore();
                }
            }

            // Проверка на сброс мусора на базе
            if (player.getBoundsInParent().intersects(baseImageView.getBoundsInParent())) {
                trashCollected = 0;
                updateScore();
            }

            // Проверка на завершение игры
            if (totalTrashCollected >= goal) {
                gameOver = true;
                scoreText.setText("YOU WON! Total Trash Collected: " + totalTrashCollected);
            }
        }
    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) {
        for (int i = 0; i < 20; i++) {
            generateTree();
            System.out.printf("hello");
        }
        root.setPrefSize(600, 600);

        root.getChildren().add(player);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        // Добавляем базу
        baseImageView = new ImageView(baseImage);
        baseImageView.setFitWidth(120);
        baseImageView.setFitHeight(120);
        baseImageView.setX(baseX * 20);
        baseImageView.setY(baseY * 20);
        root.getChildren().add(baseImageView);

        // Добавляем текст для отображения счета
        scoreText = new Text(10, 20, "Trash Collected: " + trashCollected + " / Total: " + totalTrashCollected + " / Goal: " + goal);
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setFill(Color.BLACK);
        root.getChildren().add(scoreText);

        // Генерируем первый мусор
        generateTrash();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void generateTrash() {
        trashX = random.nextInt(30);
        trashY = random.nextInt(30);
        trashImageView = new ImageView(trashImage);
        trashImageView.setFitWidth(30);
        trashImageView.setFitHeight(30);
        trashImageView.setX(trashX * 20);
        trashImageView.setY(trashY * 20);
        root.getChildren().add(trashImageView);
    }
    private void generateTree(){
        treeX = random.nextInt(30);
        treeY = random.nextInt(30);
        treeImageView = new ImageView(treeImage);
        treeImageView.setFitWidth(60);
        treeImageView.setFitHeight(60);
        treeImageView.setX(treeX * 20);
        treeImageView.setY(treeY * 20);
        root.getChildren().add(treeImageView);
    }

    private void updateScore() {
        scoreText.setText("Trash Collected: " + trashCollected + " / Total: " + totalTrashCollected + " / Goal: " + goal);
    }
}
