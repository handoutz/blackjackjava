package com.vince.engine2d;

import lombok.Getter;

@Getter
public class GameEngine {
    private final Thread gameThread;
    private final FrameListener onFrame;

    public GameEngine(FrameListener onFrame) {
        this.onFrame = onFrame;
        gameThread = new Thread(this::runLoop);
        gameThread.start();
    }

    private void runLoop() {

    }
}
