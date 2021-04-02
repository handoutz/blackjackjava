package com.vince.engine2d;

import lombok.Getter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GameEngine {
    private final Thread gameThread;
    private final FrameListener onFrame;
    private int frame;

    public GameEngine(FrameListener onFrame) {
        this.onFrame = onFrame;
        keyEvents = new ArrayList<>();
        gameThread = new Thread(this::runLoop);
        gameThread.start();
    }

    private List<KeyEvent> keyEvents;

    public void keyChange(KeyEvent e) {
        keyEvents.add(e);
    }

    private void runLoop() {
        var start = System.currentTimeMillis();
        var lastFrameTime = 0L;
        while (true) {
            var now = System.currentTimeMillis();
            onFrame.acceptFrame(frame++, this, now - start, now - lastFrameTime);
            lastFrameTime = now;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
