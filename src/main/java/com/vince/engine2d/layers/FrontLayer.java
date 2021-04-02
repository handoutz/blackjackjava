package com.vince.engine2d.layers;

import com.vince.engine2d.GameEngine;
import com.vince.engine2d.actor.PlayerActor;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Getter
public class FrontLayer extends DrawLayer {
    private PlayerActor player;
    private BufferedImage currentLevelImage;

    public FrontLayer(int width, int height) {
        super(1, width, height);
        player = new PlayerActor(30, 30, this);
        getDrawables().add(player);
        try {
            var srcImg = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("level1_test.bmp")));
            var destImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (var x = 0; x < srcImg.getWidth(); x++) {
                for (var y = 0; y < srcImg.getHeight(); y++) {
                    var rgb = srcImg.getRGB(x, y);
                    int alpha = (rgb >> 24) & 0xFF,
                            red = (rgb >> 16) & 0xFF,
                            green = (rgb >> 8) & 0xFF,
                            blue = (rgb) & 0xFF;
                    if (red == 0xFF && green == 0xFF && blue == 0xFF) {
                        destImg.setRGB(x, y, 0x00ff0000);
                    } else {
                        destImg.setRGB(x, y, 0xffff0000);
                    }
                }
            }
            currentLevelImage=destImg;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(currentLevelImage, 0, 0, null);
        super.draw(g);
    }

    @Override
    public void acceptFrame(int frameNum, GameEngine engine) {
        super.acceptFrame(frameNum, engine);
    }
}
