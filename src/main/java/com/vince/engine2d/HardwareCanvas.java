package com.vince.engine2d;

import com.vince.engine2d.layers.BackLayer;
import com.vince.engine2d.layers.DrawLayer;
import com.vince.engine2d.layers.FrontLayer;
import com.vince.engine2d.layers.HUDLayer;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class HardwareCanvas extends Canvas {
    private int width, height;

    private List<DrawLayer> layers;

    public HardwareCanvas(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setSize(width, height);
        layers = Stream
                .of(new BackLayer(width, height), new FrontLayer(width, height), new HUDLayer(width, height))
                .collect(Collectors.toList());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D twoDee = (Graphics2D) g;
        for(var layer : layers){
            layer.draw(twoDee);
        }
    }
}
