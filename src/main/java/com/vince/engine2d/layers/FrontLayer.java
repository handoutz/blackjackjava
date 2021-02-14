package com.vince.engine2d.layers;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.actor.PlayerActor;

import java.awt.*;

public class FrontLayer extends DrawLayer{
    public FrontLayer(int width, int height) {
        super(1, width, height);
        getDrawables().add(new PlayerActor(30,30));
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        super.acceptFrame(frameNum, engine);
    }
}
