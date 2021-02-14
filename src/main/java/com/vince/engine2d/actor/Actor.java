package com.vince.engine2d.actor;

import com.vince.engine2d.Drawable;
import com.vince.engine2d.FrameListener;
import com.vince.engine2d.Position;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

@Getter
@Setter
public abstract class Actor extends Drawable implements FrameListener {
    private Position position;

    public Actor(int x, int y, int width, int height) {
        super(x, y, width, height);
        position = new Position();
    }

    protected void setXPosition(int x){
        position.setX(x);
    }

    protected void setYPosition(int y){
        position.setY(y);
    }



}
