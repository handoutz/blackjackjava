package com.vince.engine2d;

import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class GameFrame extends JFrame {
    private HardwareCanvas canvas;
    private int width, height;

    public GameFrame(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setSize(width, height);
        canvas = new HardwareCanvas(width, height);
        add(canvas);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        var frame = new GameFrame(1024, 1024);
    }
}
