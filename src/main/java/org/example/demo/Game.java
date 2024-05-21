package org.example.demo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {

    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private int goal = 20;
    private int trashX, trashY;
    private int truckX = WIDTH / 2, truckY = HEIGHT / 2;
    private int trashCollected = 0;
    private Direction direction = Direction.RIGHT;
    private long lastTick = 0;
    private long speed = 150_000_000; // 150 миллисекунд
    private int baseX = 5; // координата X базы
    private int baseY = 5; // координата Y базы
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        generateTrash();

        new AnimationTimer() {
            public void handle(long now) {
                if (now - lastTick > speed) {
                    lastTick = now;
                    tick(gc);
                }
            }
        }.start();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case DOWN:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case LEFT:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case RIGHT:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Trash Truck Game");
        primaryStage.show();
    }

    private void tick(GraphicsContext gc) {
        int prevTruckX = truckX;
        int prevTruckY = truckY;

        switch (direction) {
            case UP:
                truckY--;
                break;
            case DOWN:
                truckY++;
                break;
            case LEFT:
                truckX--;
                break;
            case RIGHT:
                truckX++;
                break;
        }

        if (truckX < 0 || truckX >= WIDTH || truckY < 0 || truckY >= HEIGHT) {
            System.out.println("Game Over");
            System.exit(0);
        }

        if (truckX == trashX && truckY == trashY) {
            if (trashCollected<=4){
                trashCollected++;
                generateTrash();
                goal--;
            }

        }

        if (truckX == baseX && truckY == baseY) {
            trashCollected = 0;
        }

        gc.clearRect(0, 0, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        // Draw truck
        gc.setFill(Color.BLUE);
        gc.fillRect(truckX * TILE_SIZE, truckY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw trailer


        // Draw trash
        gc.setFill(Color.RED);
        gc.fillRect(trashX * TILE_SIZE, trashY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw base
        gc.setFill(Color.YELLOW);
        gc.fillRect(baseX * TILE_SIZE, baseY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw collected trash count
        gc.setFill(Color.BLACK);
        gc.fillText("Trash Collected: " + trashCollected, 10, 20);
        gc.fillText("left: " + goal, 300, 20);
        if (goal == 0){
            gc.fillText("YOU WON", 150, 150);
        }
    }

    private void generateTrash() {
        trashX = (int) (Math.random() * WIDTH);
        trashY = (int) (Math.random() * HEIGHT);
        // Проверяем, чтобы мусор не появлялся на базе
        while (trashX == baseX && trashY == baseY) {
            trashX = (int) (Math.random() * WIDTH);
            trashY = (int) (Math.random() * HEIGHT);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}