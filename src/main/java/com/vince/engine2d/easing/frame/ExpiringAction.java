package com.vince.engine2d.easing.frame;

import com.vince.engine2d.FrameListener;
import com.vince.engine2d.GameEngine;
import com.vince.engine2d.actor.Actor;
import com.vince.engine2d.layers.FrontLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class ExpiringAction implements FrameListener {
    private long duration;
    private long startTime;
    private String id;


    public boolean isExpired(long timeMs) {
        var timeElapsed = timeMs - startTime;
        return timeElapsed > duration;
    }

    public long timeLeft(long timeMs) {
        return Math.abs(timeMs - (startTime+duration));
    }

    public abstract void actOn(Actor actor, int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame, FrontLayer layer);

    @Override
    public void acceptFrame(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame) {
        if (isExpired(timeMs)) {
            return;
        }
    }
}
