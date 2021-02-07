package com.vince.blackjack;

import com.vince.blackjack.gooey.ILogThings;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Hand {
    private List<Card> cards;
    private ILogThings ctx;

    public Hand(ILogThings c) {
        ctx = c;
        cards = new ArrayList<Card>();
    }

    public void print() {
        var val = 0;
        for (var cd : cards) {
            if (cd.isFaceUp()) {
                ctx.logln(cd.toString());
            } else {
                ctx.logln("********");
            }
        }
        System.out.printf("hand value = %d%n", value());
    }

    public void flipAllUp() {
        for (var cd : cards) {
            cd.setFaceUp(true);
        }
    }

    public boolean hasCardName(String card) {
        return cards.stream().anyMatch(c -> c.getName().equalsIgnoreCase(card));
    }

    public boolean isBlackJack() {
        return hasCardName("a") && (hasCardName("j") || hasCardName("q") || hasCardName("k"));
    }

    public void add(Card c) {
        cards.add(c);
    }

    public boolean isBust() {
        return value() > 21;
    }

    public int value() {
        var aces = cards.stream().filter(c -> c.getName().equals("A")).collect(Collectors.toList());
        var val = cards.stream()
                //.filter(c -> !c.getName().equals("A"))
                .map(Card::getValue)
                .reduce(0, Integer::sum);
        while (aces.size() > 0 && val > 21) {
            val -= 10;
            aces.remove(0);
        }
        return val;
        //got some aces now

    }
}
