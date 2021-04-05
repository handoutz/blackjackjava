package com.vince.engine2d.actor;

import com.vince.engine2d.Drawable;
import com.vince.engine2d.FrameListener;
import com.vince.engine2d.GameEngine;
import com.vince.engine2d.Position;
import com.vince.engine2d.easing.frame.ExpiringAction;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.physics.PhysicsFrame;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

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
        position.setX(x);
        position.setY(y);
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

    public Optional<ExpiringAction> getActionById(String id) {
        return actions.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public void addAction(ExpiringAction act) {
        if (actions.stream().noneMatch(a -> act.getId().equals(a.getId()))) {
            actions.add(act);
        }
    }

    private List<ExpiringAction> getActionsInOrder() {
        var result = new ArrayList<ExpiringAction>();
        result.addAll(actions.stream().filter(a -> a.getId().equals("up")).collect(Collectors.toList()));
        result.addAll(actions.stream().filter(a -> !a.getId().equals("up")).collect(Collectors.toList()));
        return result;
    }

    private boolean runAction(ExpiringAction action, List<ExpiringAction> removable, int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame, PhysicsFrame phys) {
        if (action.isExpired(timeMs)) {
            removable.add(action);
            return false;
        }
        action.actOn(this, frameNum, engine, timeMs, msSinceLastFrame, layer, phys);
        return true;
    }

    protected void runActions(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame, PhysicsFrame phys) {
        var removable = new ArrayList<ExpiringAction>();
        var ordered = getActionsInOrder();
        for (var action : ordered) {
            runAction(action, removable, frameNum, engine, timeMs, msSinceLastFrame, phys);
        }
        for (var expired : removable) {
            actions.remove(expired);
        }
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine, long timeMs, long msSinceLastFrame) {

    }
}