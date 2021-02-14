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
        while (true) {
            onFrame.acceptFrame(frame++, this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
