package com.vince.engine2d.actor;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.easing.frame.MovementAction;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.physics.PhysicsFrame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerActor extends Actor {

    public PlayerActor(int x, int y, FrontLayer layer) {
        super(x, y, 20, 20, layer);
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.drawRect(getPosition().getX(), getPosition().getY(), getWidth(), getHeight());
        g.setColor(Color.green);
        g.drawRect(getPosition().getX(), getPosition().getY(), 2, 2);
        g.setColor(Color.blue);
        g.drawRect(getPosition().getX() + getWidth(), getPosition().getY() + getHeight(), 2, 2);
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame) {
        var phys = new PhysicsFrame(getPosition().getX(), getPosition().getY(), getLayer().getCurrentLevelImage(), getWidth(), getHeight());
        phys.applyGravity();
        super.acceptFrame(frameNum, engine, timeMs, msSinceLastFrame);
        int xChange = 0, yChange = 0;
        int changeAmount = 2;
        var events = engine.getKeyEvents();
        for (var event : events) {
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        addAction(new MovementAction(10000, timeMs, -changeAmount, 0, "left"));
                        //xChange -= changeAmount;
                        break;
                    case KeyEvent.VK_RIGHT:
                        addAction(new MovementAction(10000, timeMs, changeAmount, 0, "right"));
                        //xChange += changeAmount;
                        break;
                    case KeyEvent.VK_UP:
                        //only jump if we're near the ground
                        if (!phys.canMoveTo(phys.getCurX(), phys.getCurY() + 25)) {
                            addAction(new MovementAction(1000, timeMs, 0, -changeAmount * 3, "up"));
                        }
                        //yChange -= changeAmount;
                        break;
                    case KeyEvent.VK_DOWN:
                        //getActions().add(new MovementAction(100, timeMs, 0, changeAmount));
                        //yChange += changeAmount;
                        break;
                }
            } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        removeActionsById("left");
                        break;
                    case KeyEvent.VK_RIGHT:
                        removeActionsById("right");
                        break;
                    case KeyEvent.VK_UP:
                        var oldAction = getActionById("up");
                        if (oldAction.isPresent() && oldAction.get().timeLeft(timeMs) > 500) {
                            oldAction.get().setDuration(500);
                        } else {
                            removeActionsById("up");
                        }
                        //yChange -= changeAmount;
                        break;
                    case KeyEvent.VK_DOWN:
                        break;
                }
            }
        }
        /*phys.applyX(xChange);
        phys.applyY(yChange);*/
        runActions(frameNum,engine,timeMs,msSinceLastFrame, phys);
        phys.applyToActor(this);
        /*setXPosition(phys.getCurX());
        setYPosition(phys.getCurY());*/
        events.clear();
        //setXPosition(getPosition().getX() + xChange);
        //setYPosition(getPosition().getY() + yChange);
    }
}
