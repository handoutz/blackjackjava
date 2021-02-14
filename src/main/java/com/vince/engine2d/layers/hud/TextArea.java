package com.vince.engine2d.layers.hud;

import com.vince.engine2d.Drawable;
import com.vince.engine2d.GameEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextArea extends Drawable {
    private List<String> lines;

    public TextArea(int x, int y, int width, int height) {
        super(x, y, width, height);
        lines = new ArrayList<>();
    }

    public void append(String str) {
        if (lines.size() > 10) {
            lines.remove(10);
        }
        lines.add(str);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
        var metrics = g.getFontMetrics();
        g.drawString(Double.toString(fps), getWidth() - 50, metrics.getHeight());
        var y = getY();
        for (var line : lines) {
            y += metrics.getHeight();
            g.drawString(line, getX(), y);
        }
    }

    private int lastSecondFrames = 0;
    private long startTime = 0;

    private double fps = 0.0;

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        lastSecondFrames++;
        var curTime = System.currentTimeMillis();
        if(startTime==0)
            startTime = curTime;
        fps = (double)frameNum / (double)(curTime - startTime);
    }
}
