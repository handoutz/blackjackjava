package com.vince.engine2d.physics;

import com.vince.engine2d.actor.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

@Getter
public class PhysicsFrame {
    private final BufferedImage physMap;

    private int curX, curY;
    private int origX, origY;

    private int width, height;

    public PhysicsFrame(int x, int y, BufferedImage img, int width, int height) {
        this.physMap = img;
        this.curX = x;
        this.curY = y;
        this.origX = x;
        this.origY = y;
        this.width = width;
        this.height = height;
    }

    private boolean inRect(int xRect, int yRect, int width, int height, int x, int y) {
        return x >= xRect && x <= xRect + width && y >= yRect && y <= yRect + height;
    }

    public boolean canMoveTo(int x, int y) {
        if (x >= physMap.getWidth() || y >= physMap.getHeight()
                || x < 0 || y < 0)
            return false;
        var rgb = physMap.getRGB(x, y);
        if (rgb != 0xFFFF0000) {
            return true;
        } else {
            return false;
        }
    }

    public void applyX(int x) {
        if (canMoveTo(curX + x, curY)) {
            curX += x;
        }
    }

    public void applyY(int y) {
        if (canMoveTo(curX, curY + y)) {
            curY += y;
        }
    }

    public void applyToActor(Actor actor) {
        if (!canMoveTo(curX, curY) || !canMoveTo(curX + getWidth(), curY)) {
            curX = origX;
        }
        if (!canMoveTo(curX, curY) || !canMoveTo(curX, curY + getHeight())) {
            curY = origY;
        }
        actor.setXPosition(curX);
        actor.setYPosition(curY);
    }

    public void applyGravity() {
        applyY(4);
    }
}
