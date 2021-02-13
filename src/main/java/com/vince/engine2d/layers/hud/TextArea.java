package com.vince.engine2d.layers.hud;

import com.vince.engine2d.Drawable;

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
        var y = getY();
        for (var line : lines) {
            y += metrics.getHeight();
            g.drawString(line, getX(), y);
        }
    }
}
