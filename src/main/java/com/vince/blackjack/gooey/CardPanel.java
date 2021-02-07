package com.vince.blackjack.gooey;

import com.vince.blackjack.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardPanel extends JPanel {
    private Card card;
    private static final int MARGIN = 3;
    private BufferedImage patternImage;

    public CardPanel(Card c) {
        card = c;
        setSize(80, 120);
        setMinimumSize(getSize());
        setPreferredSize(getSize());
        setMaximumSize(getSize());

    }

    private TexturePaint createCardTexture(int width, int height) {
        if (patternImage == null) {
            final int patternWidth = 25, patternHeight = 26;
            var buff = new BufferedImage(patternWidth, patternHeight, BufferedImage.TYPE_INT_BGR);

            int rgbBack = 0x22aa00, rgbFront = 0xaa2200;

            for (int x = 0; x < patternWidth; x++) {
                for (int y = 0; y < patternHeight; y++) {
                    if ((y + x) % 2 == 0) {
                        buff.setRGB(x, y, rgbBack);
                    } else {
                        buff.setRGB(x, y, rgbFront);
                    }
                }
            }
            patternImage = buff;
        }

        var tp = new TexturePaint(patternImage, new Rectangle(width, height));
        return tp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        var sz = getSize();
        Font arialSmall = new Font("Arial", Font.PLAIN, 20),
                arialBig = new Font("Arial", Font.BOLD, 22);
        var metrics = gg.getFontMetrics(arialSmall);
        var nameMetric = gg.getFontMetrics(arialBig);
        gg.setColor(Color.black);
        gg.drawRect(0, 0, sz.width - 1, sz.height - 1);
        if (card.isFaceUp()) {
            g.setFont(arialSmall);
            String suite = switch (card.getSuite()) {
            /*case Clubs -> "♣";
            case Diamonds -> "♦";
            case Hearts -> "♥";
            case Spades -> "♠";*/
                case Clubs -> "\u2663";
                case Diamonds -> "\u2666";
                case Hearts -> "\u2665";
                case Spades -> "\u2660";
            };
            gg.drawString(suite, MARGIN, metrics.getHeight() - MARGIN);
            //g.drawString(suite, sz.width - metrics.stringWidth(suite) - MARGIN, sz.height - metrics.getHeight() - MARGIN);
            gg.drawString(suite, sz.width - metrics.stringWidth(suite) - MARGIN, sz.height - MARGIN);
            String fmt = String.format("%s", card.getName());
            gg.setFont(arialBig);
            gg.drawString(fmt, ((sz.width) / 2) - MARGIN, ((sz.height) / 2) + MARGIN);
        } else {
            gg.setPaint(createCardTexture(sz.width - 1, sz.height - 2));
            g.fillRect(1, 1, sz.width - 1, sz.height - 2);
        }
    }
}
