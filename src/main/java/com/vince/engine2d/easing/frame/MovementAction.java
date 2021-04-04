package com.vince.engine2d.easing.frame;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.actor.Actor;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.physics.PhysicsFrame;

public class MovementAction extends ExpiringAction {
    private int xChangeFrame, yChangeFrame;


    public MovementAction(long duration, long startTime, int xChangeFrame, int yChangeFrame, String id) {
        super(duration, startTime, id);
        this.xChangeFrame = xChangeFrame;
        this.yChangeFrame = yChangeFrame;
    }

    @Override
    public void actOn(Actor actor, int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame, FrontLayer layer) {
        var phys = new PhysicsFrame(actor.getPosition().getX(), actor.getPosition().getY(), layer.getCurrentLevelImage(), actor.getWidth(), actor.getHeight());
        phys.applyX(xChangeFrame);
        phys.applyY(yChangeFrame);

        actor.setXPosition(phys.getCurX());
        actor.setYPosition(phys.getCurY());
    }
}
