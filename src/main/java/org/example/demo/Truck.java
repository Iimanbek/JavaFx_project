package org.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Truck extends Rectangle {
    private static final int SIZE = 20;

    public Truck(double x, double y) {
        super(x, y, SIZE, SIZE);
        setFill(Color.RED); // Цвет грузовика
    }
}
