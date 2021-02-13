package com.vince.blackjack.casino;

import com.vince.blackjack.GameState;
import com.vince.blackjack.events.EventHandler;
import com.vince.blackjack.gooey.ILogThings;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class Casino {
    private Bank playerBank;
    private Bank currentBet;
    private GameState state;
    private ILogThings logger;

    public EventHandler<Bank> onPlayerBankChange;
    public EventHandler<Bank> onBetChange;

    public Casino(GameState state, ILogThings logger) {
        this.state = state;
        this.logger = logger;
        onPlayerBankChange = new EventHandler<>();
        onBetChange = new EventHandler<>();
        playerBank = new Bank("Player");
        currentBet = new Bank("Betting");

        state.onStateChange.subscribe(this::acceptStateChange);
    }

    public int getWallet() {
        return playerBank.getAmount();
    }

    public int getBet() {
        return currentBet.getAmount();
    }

    public void initDefaultWallet() {
        playerBank.addNewChip(ChipValue.ONE, 10);
        playerBank.addNewChip(ChipValue.FIVE, 5);
        playerBank.addNewChip(ChipValue.TEN, 3);
        playerBank.addNewChip(ChipValue.TWENTY_FIVE, 2);
        playerBank.addNewChip(ChipValue.FIFTY, 1);
        onPlayerBankChange.accept(playerBank);
    }

    public void betChip(Chip chip) {
        playerBank.removeChip(chip);
        currentBet.addChip(chip);

        onPlayerBankChange.accept(playerBank);
        onBetChange.accept(currentBet);
    }


    public void acceptStateChange(GameState.State state) {
        if (state == GameState.State.DEALER_WIN || state == GameState.State.PLAYER_BUST) {
            //loss!
        }
        if (state == GameState.State.WIN || state == GameState.State.DEALER_BUST) {

        }
        if (state == GameState.State.BLACKJACK) {

        }
        if (state == GameState.State.PUSH) {

        }
    }
}
