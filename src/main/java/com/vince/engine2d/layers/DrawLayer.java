package com.vince.engine2d.layers;

import com.vince.engine2d.Drawable;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class DrawLayer {

    private int zIndex;
    private int width, height;

    private List<Drawable> drawables;

    public DrawLayer(int zIndex, int width, int height) {
        this.zIndex = zIndex;
        this.width = width;
        this.height = height;
        drawables = new ArrayList<>();
    }

    public void draw(Graphics2D graphics) {
        for (var drawable : drawables) {
            drawable.draw(graphics);
        }
    }
}
