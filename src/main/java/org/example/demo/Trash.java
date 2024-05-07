package org.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Trash extends Circle {
    public static final int RADIUS = 10;

    public Trash(double x, double y) {
        super(x, y, RADIUS);
        setFill(Color.GREEN); // Цвет мусора
    }
}

