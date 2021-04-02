package com.vince.engine2d.actor;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.physics.PhysicsFrame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerActor extends Actor {
    private final FrontLayer layer;

    public PlayerActor(int x, int y, FrontLayer layer) {
        super(x, y, 0, 0);
        this.layer = layer;
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.drawRect(getPosition().getX(), getPosition().getY(), 20, 20);
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        var phys = new PhysicsFrame(getPosition().getX(), getPosition().getY(), layer.getCurrentLevelImage());
        phys.applyGravity();
        int xChange = 0, yChange = 0;
        int changeAmount = 5;
        var events = engine.getKeyEvents();
        for (var event : events) {
            if (event.getID() == KeyEvent.KEY_PRESSED || event.getID() == KeyEvent.KEY_RELEASED) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        xChange -= changeAmount;
                        break;
                    case KeyEvent.VK_RIGHT:
                        xChange += changeAmount;
                        break;
                    case KeyEvent.VK_UP:
                        yChange -= changeAmount;
                        break;
                    case KeyEvent.VK_DOWN:
                        yChange += changeAmount;
                        break;
                }
            }

        }
        
        events.clear();
        setXPosition(phys.getCurX());
        setYPosition(phys.getCurY());
        //setXPosition(getPosition().getX() + xChange);
        //setYPosition(getPosition().getY() + yChange);
    }
}
