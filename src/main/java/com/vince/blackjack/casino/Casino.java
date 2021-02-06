package com.vince.blackjack.casino;

import com.vince.blackjack.GameState;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class Casino {

    private int wallet;
    private int bet;

    private Optional<Consumer<Casino>> betUpdated;
    private Optional<Consumer<Casino>> walletUpdated;

    public Casino(Optional<Consumer<Casino>> betUpdated, Optional<Consumer<Casino>> walletUpdated, int walletStart) {
        this.betUpdated = betUpdated;
        this.walletUpdated = walletUpdated;
        this.wallet = walletStart;
    }

    public int getWallet() {
        return wallet;
    }

    public int getBet() {
        return bet;
    }

    public void addChipToWallet(Chip... aChips) {
        var chips = Arrays.stream(aChips).collect(Collectors.toList());
        wallet += chips.stream().map(c -> c.getValue()).reduce(0, Integer::sum);
        walletUpdated.ifPresent(c -> c.accept(this));
    }

    public void betChip(Chip chip) {
        //todo: verify wallet has the monies
        bet += chip.getValue();
        wallet -= chip.getValue();
        betUpdated.ifPresent(c -> c.accept(this));
    }


    public void acceptStateChange(GameState.State state) {
        if (state == GameState.State.DEALER_WIN || state == GameState.State.PLAYER_BUST) {
            //loss!
            wallet -= bet;
            bet = 0;
        }
        if (state == GameState.State.WIN || state == GameState.State.DEALER_BUST) {
            wallet += bet * 2;
            bet = 0;
        }
        if (state == GameState.State.BLACKJACK) {
            wallet += bet * 2;
            wallet += bet / 2;
            bet = 0;
        }
        if (state == GameState.State.PUSH) {
            wallet += bet;
            bet = 0;
        }
        walletUpdated.ifPresent(c -> c.accept(this));
        betUpdated.ifPresent(c -> c.accept(this));
    }
}
