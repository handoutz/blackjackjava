package com.vince.blackjack;

import com.vince.blackjack.events.EventHandler;
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
        BLACKJACK("Blackjack!", Color.GREEN),
        DEALER_WIN("Dealer wins!", Color.red),
        PUSH("Push", Color.yellow),
        PLAYER_BUST("You busted!", Color.red);
        public String description;
        public Color color;

        State(String desc, Color col) {
            this.description = desc;
            this.color = col;
        }

        public boolean isPlayerLoss() {
            return this == DEALER_WIN || this == PLAYER_BUST;
        }

        public boolean isPlayerWin() {
            return this == WIN || this == BLACKJACK || this == PUSH || this == DEALER_BUST;
        }
    }

    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private ILogThings ctx;


    public EventHandler<GameState> onHit;
    public EventHandler<GameState> onStand;
    public EventHandler<GameState> onHandUpdate;
    public EventHandler<GameState> onReset;
    public EventHandler<GameState> onStateReset;
    public EventHandler<State> onStateChange;

    private State currentState;

    public GameState(ILogThings c) {
        ctx = c;
        onHit = new EventHandler<>();
        onStand = new EventHandler<>();
        onHandUpdate = new EventHandler<>();
        onReset = new EventHandler<>();
        onStateReset = new EventHandler<>();
        onStateChange = new EventHandler<>();
        deck = new Deck(c);
        currentState = State.PLAYING;
        reset();
    }

    public void reset() {
        ctx.logln();
        ctx.logln();
        playerHand = new Hand(ctx);
        dealerHand = new Hand(ctx);

        onStateReset.accept(this);
        setState(State.PLAYING);
    }

    public void setState(State s) {
        currentState = s;
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

        if (playerHand.isBlackJack()) {
            setState(State.BLACKJACK);
            stand();
            return;
        }
        if (dealerHand.isBlackJack()) {
            setState(State.DEALER_WIN);
            stand();
        }
    }

    public void printState() {
        /*ctx.logln(" --- Dealer ---");
        dealerHand.print();

        ctx.logln();
        ctx.logln(" --- Player ---");
        playerHand.print();*/
    }

    public boolean hit() {
        var next = deck.nextCard();
        playerHand.add(next);
        ctx.logln("You drew a %s".formatted(next.getName()));
        onHandUpdate.accept(this);
        onHit.accept(this);
        if (playerHand.isBust()) {
            ctx.logln("BUSTED!");
            setState(State.PLAYER_BUST);
            stand();
            return false;
        }
        return true;
    }

    public void stand() {
        dealerHand.flipAllUp();
        onHandUpdate.accept(this);
        if (currentState.isPlayerLoss()) {
            setState(State.DEALER_WIN);
            return;
        }
        if (currentState == State.BLACKJACK) {
            setState(State.BLACKJACK);
            ctx.logln("you win!");
            return;
        }
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
