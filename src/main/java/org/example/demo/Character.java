package org.example.demo;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Character extends Pane {

    private final ImageView imageView;
    private final int count = 3;
    private final int columns = 3;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final int width = 32;
    private final int height = 32;
    private int score = 0;
    private Rectangle removeRect = null;
    public final SpriteAnimation animation;

    public Character(ImageView imageView) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new SpriteAnimation(imageView, Duration.millis(200), count, columns, offsetX, offsetY, width, height);
        getChildren().add(imageView);
    }

    public void moveX(int x) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            setTranslateX(getTranslateX() + (right ? 1 : -1));

//            checkBonusCollision();

        }
    }

    public void moveY(int y) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            setTranslateY(getTranslateY() + (down ? 1 : -1));
//            checkBonusCollision();
        }
    }

//    private void checkBonusCollision() {
//        Main.bonuses.stream()
//                .filter(rect -> getBoundsInParent().intersects(rect.getBoundsInParent()))
//                .findFirst()
//                .ifPresent(rect -> {
//                    removeRect = rect;
//                    score++;
//                    System.out.println(score);
//                    Main.bonuses.remove(removeRect);
//                    Main.root.getChildren().remove(removeRect);
//                });
//    }

    public void handleKeyPress(KeyCode code) {
    }
}
