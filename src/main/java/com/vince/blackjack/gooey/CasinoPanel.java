package com.vince.blackjack.gooey;

import com.vince.blackjack.casino.Bank;
import com.vince.blackjack.casino.Casino;

import javax.swing.*;

public class CasinoPanel extends JPanel {
    private Casino casino;

    public CasinoPanel() {

    }

    public void setCasino(Casino casino) {
        this.casino = casino;
        casino.onBetChange.subscribe(this::onBetChange);
        casino.onPlayerBankChange.subscribe(this::onWalletChange);
        casino.initDefaultWallet();
    }


    private void onWalletChange(Bank bet) {

    }

    private void onBetChange(Bank bet) {

    }

}
