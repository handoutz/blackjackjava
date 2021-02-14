package com.vince.engine2d;

import com.vince.engine2d.layers.BackLayer;
import com.vince.engine2d.layers.DrawLayer;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.layers.HUDLayer;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class HardwareCanvas extends Canvas implements FrameListener {
    private int width, height;

    private List<DrawLayer> layers;
    private GameEngine engine;


    public HardwareCanvas(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setSize(width, height);
        layers = Stream
                .of(new BackLayer(width, height), new FrontLayer(width, height), new HUDLayer(width, height))
                .collect(Collectors.toList());
        engine = new GameEngine(this);
        var kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        //kfm.addKeyEventPostProcessor(this::onKeySomething);
        kfm.addKeyEventDispatcher(this::onKeySomething);
        requestFocus();
    }

    private boolean onKeySomething(KeyEvent e) {
        engine.keyChange(e);
        return false;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D twoDee = (Graphics2D) g;
        for (var layer : layers) {
            layer.draw(twoDee);
        }
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        for(var layer : layers){
            layer.acceptFrame(frameNum, engine);
        }
        repaint();
    }
}
