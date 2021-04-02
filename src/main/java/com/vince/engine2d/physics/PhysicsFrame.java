package com.vince.engine2d.physics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

@Getter
public class PhysicsFrame {
    private final BufferedImage physMap;

    private int curX, curY;

    public PhysicsFrame(int x, int y, BufferedImage img) {
        this.physMap = img;
        this.curX = x;
        this.curY = y;
    }



    public void applyGravity() {
        curY += 4;
    }
}
