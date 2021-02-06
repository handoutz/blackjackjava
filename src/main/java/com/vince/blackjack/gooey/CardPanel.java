package com.vince.blackjack.gooey;

import com.vince.blackjack.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardPanel extends JPanel {
    private Card card;
    private static final int MARGIN = 5;

    public CardPanel(Card c) {
        card = c;
        setSize(40, 60);
        setMinimumSize(getSize());
        setPreferredSize(getSize());
        setMaximumSize(getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var gg = (Graphics2D) g;
        var sz = getSize();
        Font arialSmall = new Font("Arial", Font.PLAIN, 12),
                arialBig = new Font("Arial", Font.PLAIN, 16);
        var metrics = gg.getFontMetrics(arialSmall);
        var nameMetric = gg.getFontMetrics(arialBig);
        g.setColor(Color.black);
        g.drawRect(0, 0, sz.width - 1, sz.height - 1);
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
            g.drawString(suite, MARGIN, MARGIN + metrics.getHeight());
            //g.drawString(suite, sz.width - metrics.stringWidth(suite) - MARGIN, sz.height - metrics.getHeight() - MARGIN);
            g.drawString(suite, sz.width - metrics.stringWidth(suite) - MARGIN, sz.height - MARGIN);
            String fmt = String.format("%s", card.getName());
            g.setFont(arialBig);
            g.drawString(fmt, ((sz.width) / 2) - MARGIN, ((sz.height) / 2) - MARGIN + nameMetric.getHeight());
        } else {
            g.setColor(Color.red);
            g.fillRect(1, 1, sz.width - 1, sz.height - 1);
        }
    }
}
