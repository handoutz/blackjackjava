package com.vince.blackjack.casino;

import com.vince.blackjack.GameState;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class Casino {
    private Bank playerBank;
    private Bank currentBet;

    public Casino() {
        playerBank = new Bank("Player");
        currentBet = new Bank("Betting");
    }

    public int getWallet() {
        return playerBank.getAmount();
    }

    public int getBet() {
        return currentBet.getAmount();
    }

    public void betChip(Chip chip) {

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
