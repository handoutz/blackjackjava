package com.vince.blackjack;

import com.vince.blackjack.gooey.ILogThings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
public class GameState {
    public enum State {
        PLAYING("", Color.GREEN),
        WIN("Player win", Color.GREEN),
        DEALER_BUST("Dealer bust. Player wins!", Color.GREEN),
        DEALER_WIN("Dealer wins!", Color.red),
        PUSH("Push", Color.yellow),
        PLAYER_BUST("You busted!", Color.red);
        public String description;
        public Color color;

        State(String desc, Color col) {
            this.description = desc;
            this.color = col;
        }
    }

    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private ILogThings ctx;


    private Consumer<GameState> onHit;
    private Consumer<GameState> onStand;
    private Consumer<GameState> onHandUpdate;
    private Consumer<GameState> onReset;
    private Consumer<GameState> onStateReset;
    private Consumer<State> onStateChange;


    public GameState(ILogThings c) {
        ctx = c;
        deck = new Deck();
        reset();
    }

    public void reset() {
        ctx.logln();
        ctx.logln();
        playerHand = new Hand(ctx);
        dealerHand = new Hand(ctx);

        if (onStateReset != null)
            onStateReset.accept(this);
        setState(State.PLAYING);
    }

    public void setState(State s) {
        if (onStateChange != null)
            onStateChange.accept(s);
    }

    public void deal() {
        var fdown = deck.nextCard();
        fdown.setFaceUp(false);
        dealerHand.add(fdown);
        playerHand.add(deck.nextCard());

        dealerHand.add(deck.nextCard());
        playerHand.add(deck.nextCard());

        onHandUpdate.accept(this);
        setState(State.PLAYING);
    }

    public void printState() {
        ctx.logln(" --- Dealer ---");
        dealerHand.print();

        ctx.logln();
        ctx.logln(" --- Player ---");
        playerHand.print();
    }

    public boolean hit() {
        playerHand.add(deck.nextCard());
        onHandUpdate.accept(this);
        onHit.accept(this);
        if (playerHand.isBust()) {
            ctx.logln("BUSTED!");
            stand();
            setState(State.PLAYER_BUST);
            return false;
        }
        return true;
    }

    public void stand() {
        dealerHand.flipAllUp();
        onHandUpdate.accept(this);
        while (dealerHand.value() < 17) {
            dealerHand.add(deck.nextCard());
            onHandUpdate.accept(this);
        }
        onStand.accept(this);
        printState();
        if (dealerHand.isBust()) {
            ctx.logln("dealer busted!");
            setState(State.DEALER_BUST);
            return;
        }
        var playerVal = playerHand.value();
        var dealerVal = dealerHand.value();
        if (dealerHand.value() < playerVal) {
            ctx.logln("you win!");
            setState(State.WIN);
        } else if (dealerVal == playerVal) {
            setState(State.PUSH);
        } else {
            setState(State.DEALER_WIN);
        }
    }


    public void printDeck() {
        for (var cd : deck.getCards()) {
            ctx.logln(cd.toString());
        }
    }
}
