package com.vince.engine2d.layers;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.layers.hud.TextArea;

import java.awt.*;

public class HUDLayer extends DrawLayer {
    private TextArea logArea;

    public HUDLayer(int width, int height) {
        super(0, width, height);
        logArea = new TextArea(0, 0, 256, 256);
        getDrawables().add(logArea);
        logArea.append("Hello, World!");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setColor(Color.blue);
        g.drawString("hello, world", 0, getHeight() / 2);
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        super.acceptFrame(frameNum, engine);
    }
}
