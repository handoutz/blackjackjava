package com.vince.blackjack.gooey;

import com.vince.blackjack.GameState;
import com.vince.blackjack.Hand;
import com.vince.blackjack.casino.Bank;
import com.vince.blackjack.casino.Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainForm extends JFrame implements ILogThings {
    GameState state;
    Casino casino;

    JPanel basePanel, botPanel, textPanel, topPanel, playerHand, dealerHand, valuePanel;
    JLabel playerValue, dealerValue, winLabel;
    JTextArea logArea;
    JButton hitButton, stayButton, newGameButton;
    CasinoPanel casinoPanel;

    public MainForm() {
        super("blackjacks");
        init();
    }

    private JPanel createPanel(int layout) {
        var p = new JPanel();
        p.setAlignmentX(1f);
        p.setLayout(new BoxLayout(p, layout));
        return p;
    }

    public void init() {
        basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);

        botPanel = createPanel(BoxLayout.X_AXIS);
        textPanel = createPanel(BoxLayout.Y_AXIS);
        topPanel = createPanel(BoxLayout.X_AXIS);
        playerHand = createPanel(BoxLayout.X_AXIS);
        dealerHand = createPanel(BoxLayout.X_AXIS);
        valuePanel = createPanel(BoxLayout.X_AXIS);
        casinoPanel = new CasinoPanel();
        casinoPanel.setAlignmentX(1f);
        casinoPanel.setLayout(new BoxLayout(casinoPanel, BoxLayout.Y_AXIS));

        dealerHand.setAlignmentX(-1f);

        topPanel.add(playerHand);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(dealerHand);

        hitButton = new JButton("Hit");
        //hitButton.setMnemonic('H');
        stayButton = new JButton("Stay");
        //stayButton.setMnemonic('S');
        newGameButton = new JButton("New Game");
        //newGameButton.setMnemonic('N');

        botPanel.add(hitButton);
        botPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        botPanel.add(stayButton);
        botPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        botPanel.add(newGameButton);
        botPanel.add(Box.createHorizontalGlue());


        playerValue = new JLabel("Value: ");
        dealerValue = new JLabel("Value: ");
        winLabel = new JLabel("");
        winLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valuePanel.add(playerValue);
        valuePanel.add(Box.createHorizontalGlue());
        valuePanel.add(winLabel);
        valuePanel.add(Box.createHorizontalGlue());
        valuePanel.add(dealerValue);

        basePanel.add(topPanel);
        basePanel.add(valuePanel);
        basePanel.add(Box.createVerticalStrut(20));
        basePanel.add(botPanel);
        basePanel.add(Box.createVerticalGlue());
        basePanel.add(casinoPanel);
        basePanel.add(Box.createVerticalGlue());
        basePanel.add(textPanel);
        basePanel.add(Box.createVerticalGlue());
        basePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 800);

        logArea = new JTextArea();
        logArea.setEnabled(false);
        logArea.setForeground(Color.white);
        logArea.setBackground(Color.black);
        //logArea.setSize(600, 800);

        textPanel.add(logArea);
        setVisible(true);

        state = new GameState(this);
        casino = new Casino(state, this);
        setupCasino();

        hitButton.addActionListener(this::hitButton_hit);
        stayButton.addActionListener(this::stayButton_hit);
        newGameButton.addActionListener(this::newGameButton_hit);
        state.onHit.subscribe(this::stateHit);
        state.onStand.subscribe(this::stateStand);
        state.onReset.subscribe(this::stateReset);
        state.onHandUpdate.subscribe(this::onHandUpdate);
        state.onStateReset.subscribe(this::stateReset);
        state.onStateChange.subscribe(this::stateChanged);

        var kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        //kfm.addKeyEventPostProcessor(this::onKeySomething);
        kfm.addKeyEventDispatcher(this::onKeySomething);

        setFocusable(true);
        this.requestFocus();

        newGameButton_hit(null);
    }

    private void setupCasino() {
        casinoPanel.setCasino(casino);
    }

    private boolean onKeySomething(KeyEvent e) {
        if (e.getID() != KeyEvent.KEY_RELEASED)
            return false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_H:
                hitButton_hit(null);
                return true;
            case KeyEvent.VK_S:
                stayButton_hit(null);
                return true;
            case KeyEvent.VK_N:
                newGameButton_hit(null);
                return true;
            default:
                return false;
        }
    }

    private void stateChanged(GameState.State s) {
        if (s != GameState.State.PLAYING) {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        } else {
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
        }
        winLabel.setForeground(s.color);
        winLabel.setText(s.description);
    }

    private void refreshHand(JPanel pnl, Hand hand) {
        pnl.removeAll();
        for (var c : hand.getCards()) {
            pnl.add(new CardPanel(c));
        }
        pnl.revalidate();
    }

    private void onHandUpdate(GameState state) {
        refreshHand(dealerHand, state.getDealerHand());
        refreshHand(playerHand, state.getPlayerHand());
        playerValue.setText("Value: %d".formatted(state.getPlayerHand().value()));
    }

    private void stateStand(GameState e) {
        dealerValue.setText("Value: %d".formatted(state.getDealerHand().value()));
    }

    private void stateReset(GameState e) {
        dealerValue.setText("Value:");
        logArea.setText("");
    }

    private void stateHit(GameState e) {

    }

    private void onReset(GameState state) {
    }

    private void hitButton_hit(ActionEvent e) {
        if (hitButton.isEnabled())
            state.hit();
    }

    private void stayButton_hit(ActionEvent e) {
        if (stayButton.isEnabled())
            state.stand();
    }

    private void newGameButton_hit(ActionEvent e) {
        state.reset();
        state.deal();
        state.printState();
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","True");
        var frm = new MainForm();

    }

    @Override
    public void logln() {
        logArea.append("\n");
    }

    @Override
    public void logln(String str) {
        logArea.append(str);
        logArea.append("\n");
    }
}
