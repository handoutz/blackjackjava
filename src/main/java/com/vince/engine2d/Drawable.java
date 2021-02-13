package com.vince.engine2d;

import lombok.Getter;

import java.awt.*;

@Getter
public abstract class Drawable {
    public Drawable(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    private int width, height;
    private int x, y;

    public abstract void draw(Graphics2D g);
}
