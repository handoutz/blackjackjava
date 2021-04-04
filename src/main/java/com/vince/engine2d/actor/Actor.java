package com.vince.engine2d.actor;

import com.vince.engine2d.Drawable;
import com.vince.engine2d.FrameListener;
import com.vince.engine2d.GameEngine;
import com.vince.engine2d.Position;
import com.vince.engine2d.easing.frame.ExpiringAction;
import com.vince.engine2d.layers.FrontLayer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Getter
@Setter
public abstract class Actor extends Drawable implements FrameListener {
    private Position position;
    private List<ExpiringAction> actions;
    private FrontLayer layer;

    public Actor(int x, int y, int width, int height, FrontLayer layer) {
        super(x, y, width, height);
        actions = new ArrayList<>();
        position = new Position();
        this.layer = layer;
    }

    public void setXPosition(int x) {
        position.setX(x);
    }

    public void setYPosition(int y) {
        position.setY(y);
    }

    public void removeActionsById(String id) {
        actions.removeIf(a -> a.getId().equals(id));
    }

    public void addAction(ExpiringAction act) {
        if (actions.stream().noneMatch(a -> act.getId().equals(a.getId()))) {
            actions.add(act);
        }
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame) {
        var removable = new ArrayList<ExpiringAction>();
        for (var action : actions) {
            if (action.isExpired(timeMs)) {
                removable.add(action);
                continue;
            }
            action.actOn(this, frameNum, engine, timeMs, msSinceLastFrame, layer);
        }
        for (var expired : removable) {
            actions.remove(expired);
        }
    }
}