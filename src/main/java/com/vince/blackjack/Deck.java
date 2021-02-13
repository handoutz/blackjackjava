package com.vince.blackjack;

import com.vince.blackjack.gooey.ILogThings;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Deck {
    private List<Card> cards;
    private ILogThings ctx;

    public Deck(ILogThings c) {
        cards = new ArrayList<Card>();
        ctx = c;
        reset();
    }

    /***
     * adds one deck worth of cards to the pile
     */
    public void addDeck() {
        var suites = Stream.of(Suite.Clubs, Suite.Diamonds, Suite.Hearts, Suite.Spades).collect(Collectors.toList());
        for (var suite : suites) {
            //,"J","Q","K","A"
            var numbered = Stream.of("2", "3", "4", "5", "6", "7", "8", "9")
                    .map(s -> Card.builder().suite(suite).name(s).value(Integer.parseInt(s)).build())
                    .collect(Collectors.toList());
            numbered.add(Card.builder().suite(suite).name("J").value(10).build());
            numbered.add(Card.builder().suite(suite).name("Q").value(10).build());
            numbered.add(Card.builder().suite(suite).name("K").value(10).build());
            numbered.add(Card.builder().suite(suite).name("A").value(11).build());
            cards.addAll(numbered);
        }
        shuffle();
    }

    public Card nextCard() {
        if (cards.size() == 0) {
            ctx.logln("ran out of cards. new deck!");
            reset();
        }
        return cards.remove(0);
    }

    public void reset() {
        cards.clear();
        addDeck();
        addDeck();
        addDeck();
        shuffle(30);
    }

    private void swap(int a, int b) {
        var tmp = cards.get(a);
        cards.set(a, cards.get(b));
        cards.set(b, tmp);
    }

    public void shuffle() {
        var len = cards.size();
        var rand = new Random();
        for (int i = 0; i < 1024; i++) {
            swap(rand.nextInt(len), rand.nextInt(len));
        }
    }

    public void shuffle(int times) {
        for (int i = 0; i < times; i++) {
            shuffle();
        }
    }
}
