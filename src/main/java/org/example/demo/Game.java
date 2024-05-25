package org.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Game extends Application {

    private final Pane root = new Pane();
    private final List<Trash> trashes = new ArrayList<>();
    private final Random random = new Random();
    private final TrashTypeManager trashTypeManager = new TrashTypeManager();
    private ImageView burnableBin;
    private ImageView nonBurnableBin;
    private ImageView foodWasteBin;
    private Text scoreText;
    private int score = 0;
    private int trashCount = 0;
    private final int maxTrashCount = 20; // 20 пар мусора

    private final Image burnableBinImage = new Image(getClass().getResource("/games/burnable_bin.png").toString());
    private final Image nonBurnableBinImage = new Image(getClass().getResource("/games/non_burnable_bin.png").toString());
    private final Image foodWasteBinImage = new Image(getClass().getResource("/games/food_waste_bin.png").toString());

    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer correctPlayer;

    @Override
    public void start(Stage primaryStage) {
        root.setPrefSize(800, 600);
        root.setStyle("-fx-background-color: white;");

        // Добавляем корзины для сортировки мусора
        burnableBin = new ImageView(burnableBinImage);
        nonBurnableBin = new ImageView(nonBurnableBinImage);
        foodWasteBin = new ImageView(foodWasteBinImage);

        burnableBin.setFitWidth(100);
        burnableBin.setFitHeight(100);
        burnableBin.setX(150);
        burnableBin.setY(500);

        nonBurnableBin.setFitWidth(100);
        nonBurnableBin.setFitHeight(100);
        nonBurnableBin.setX(350);
        nonBurnableBin.setY(500);

        foodWasteBin.setFitWidth(100);
        foodWasteBin.setFitHeight(100);
        foodWasteBin.setX(550);
        foodWasteBin.setY(500);

        root.getChildren().addAll(burnableBin, nonBurnableBin, foodWasteBin);

        // Добавляем текст для отображения счета
        scoreText = new Text(10, 20, "Score: " + score);
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setFill(Color.BLACK);
        root.getChildren().add(scoreText);

        // Создаем сцену и добавляем корень
        Scene scene = new Scene(root);

        // Анимация мусорок
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private long trashAddInterval = 1_000_000_000; // Интервал в наносекундах (1 секунда)

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= trashAddInterval && trashCount < maxTrashCount) {
                    generateTrash();
                    lastUpdate = now;
                }
                update();
                checkGameEnd();
            }
        };
        timer.start();

        primaryStage.setTitle("Trash Sorting Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Воспроизводим фоновую музыку
        playBackgroundMusic();
        primaryStage.setOnCloseRequest(event -> stop());

    }
    public void stop() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        };
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    };
    public static void main(String[] args) {
        launch(args);
    }

    private void generateTrash() {
        for (int i = 0; i < 1; i++) { // Добавляем по одному мусору за раз
            TrashType type = getRandomTrashType();
            Image trashImage = trashTypeManager.getRandomImage(type);

            ImageView trashImageView = new ImageView(trashImage);
            trashImageView.setFitWidth(50);
            trashImageView.setFitHeight(50);
            trashImageView.setX(random.nextInt(750));
            trashImageView.setY(random.nextInt(200));

            Trash trash = new Trash(trashImageView, type);
            trashImageView.setOnMousePressed(this::handleMousePressed);
            trashImageView.setOnMouseDragged(this::handleMouseDragged);
            trashImageView.setOnMouseReleased(this::handleMouseReleased);

            trashes.add(trash);
            root.getChildren().add(trashImageView);
        }
        trashCount += 1; // Увеличиваем счетчик мусора
    }

    private TrashType getRandomTrashType() {
        int randomType = random.nextInt(3);
        if (randomType == 0) {
            return TrashType.BURNABLE;
        } else if (randomType == 1) {
            return TrashType.NON_BURNABLE;
        } else {
            return TrashType.FOOD_WASTE;
        }
    }

    private void handleMousePressed(MouseEvent event) {
        ImageView trash = (ImageView) event.getSource();
        trash.toFront();
    }

    private void handleMouseDragged(MouseEvent event) {
        ImageView trash = (ImageView) event.getSource();
        trash.setX(event.getSceneX() - trash.getFitWidth() / 2);
        trash.setY(event.getSceneY() - trash.getFitHeight() / 2);
    }

    private void handleMouseReleased(MouseEvent event) {
        ImageView trash = (ImageView) event.getSource();
        checkSorting(trash);
    }

    private void checkSorting(ImageView trashImageView) {
        Trash trash = trashes.stream().filter(t -> t.imageView == trashImageView).findFirst().orElse(null);
        if (trash == null) return;

        if (trash.type == TrashType.BURNABLE && burnableBin.getBoundsInParent().intersects(trash.imageView.getBoundsInParent())) {
            score++;
            playCorrectSound();
            root.getChildren().remove(trash.imageView);
            trashes.remove(trash);
        } else if (trash.type == TrashType.NON_BURNABLE && nonBurnableBin.getBoundsInParent().intersects(trash.imageView.getBoundsInParent())) {
            score++;
            playCorrectSound();
            root.getChildren().remove(trash.imageView);
            trashes.remove(trash);
        } else if (trash.type == TrashType.FOOD_WASTE && foodWasteBin.getBoundsInParent().intersects(trash.imageView.getBoundsInParent())) {
            score++;
            playCorrectSound();
            root.getChildren().remove(trash.imageView);
            trashes.remove(trash);
        } else {
            playInCorrectSound();
        }

        updateScore();
    }

    private void update() {
        // Логика для падения мусора
        List<Trash> trashToRemove = new ArrayList<>();
        for (Trash trash : trashes) {
            trash.imageView.setY(trash.imageView.getY() + 1); // Замедляем падение мусора
            if (trash.imageView.getY() > 600) {
                // Мусор упал до конца экрана, можно добавить логику для штрафов
                root.getChildren().remove(trash.imageView);
                trashToRemove.add(trash);
            }
        }
        trashes.removeAll(trashToRemove);
        updateScore();
    }

    private void checkGameEnd() {
        if (trashCount >= maxTrashCount && trashes.isEmpty()) {
            displayResult();
        }
    }

    private void displayResult() {
        int stars = calculateStars();
        String resultText = "Game Over\nScore: " + score + "\nStars: " + stars;
        Text resultDisplay = new Text(200, 300, resultText);
        resultDisplay.setFont(Font.font("Arial", 40));
        resultDisplay.setFill(Color.BLACK);
        root.getChildren().add(resultDisplay);

        // Остановить фоновую музыку
        backgroundMusicPlayer.stop();


    }

    private int calculateStars() {
        if (score >= 20) {
            return 3;
        } else if (score >= 17) {
            return 2;
        } else if (score >= 13) {
            return 1;
        } else {
            return 0;
        }
    }

    private void updateScore() {

        scoreText.setText("Score: " + score);
    }

    private void playInCorrectSound() {
        Media failMusik = new Media(getClass().getResource("/smw_thud.wav").toString());
        MediaPlayer failPlayer = new MediaPlayer(failMusik);
        failPlayer.play();
    }

    private void playCorrectSound() {
        Media correctMusik = new Media(getClass().getResource("/smw_coin.wav").toString());
        correctPlayer = new MediaPlayer(correctMusik);
        correctPlayer.play();
    }

    private void playBackgroundMusic() {
        Media backgroundMusic = new Media(getClass().getResource("/runman.mp3").toString());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO)); // Зацикливание музыки
        backgroundMusicPlayer.play();
    }

    // Перечисление типов мусора
    enum TrashType {
        BURNABLE, NON_BURNABLE, FOOD_WASTE
    }

    // Класс для хранения мусора и его типа
    class Trash {
        ImageView imageView;
        TrashType type;

        Trash(ImageView imageView, TrashType type) {
            this.imageView = imageView;
            this.type = type;
        }
    }

    // Класс для управления изображениями мусора
    class TrashTypeManager {
        private final Map<TrashType, Image[]> trashImages = new HashMap<>();
        private final Random random = new Random();

        public TrashTypeManager() {
            // Добавьте все типы мусора и их изображения
            trashImages.put(TrashType.BURNABLE, new Image[]{
                    new Image(getClass().getResource("/games/cup_paper.png").toString()),
                    new Image(getClass().getResource("/games/paper.png").toString())
                    // Добавьте еще изображения, если нужно
            });
            trashImages.put(TrashType.NON_BURNABLE, new Image[]{
                    new Image(getClass().getResource("/games/computer.png").toString()),
                    new Image(getClass().getResource("/games/cup.png").toString())
                    // Добавьте еще изображения, если нужно
            });
            trashImages.put(TrashType.FOOD_WASTE, new Image[]{
                    new Image(getClass().getResource("/games/apple.png").toString()),
                    new Image(getClass().getResource("/games/banana_peal.png").toString())
                    // Добавьте еще изображения, если нужно
            });
        }

        public Image getRandomImage(TrashType type) {
            Image[] images = trashImages.get(type);
            return images[random.nextInt(images.length)];
        }
    }
}
