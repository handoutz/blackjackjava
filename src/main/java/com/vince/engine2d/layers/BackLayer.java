package com.vince.engine2d.layers;

import java.awt.*;

public class BackLayer extends DrawLayer {
    public BackLayer(int width, int height) {
        super(2, width, height);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
