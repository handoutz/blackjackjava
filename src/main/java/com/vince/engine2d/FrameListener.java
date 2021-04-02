package com.vince.engine2d;

public interface FrameListener {
    void acceptFrame(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame);
}
