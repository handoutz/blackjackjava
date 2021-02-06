package com.vince.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainProg {
    public static void main(String[] args) throws IOException {
        var state = new GameState(null);
        var buff = new BufferedReader(new InputStreamReader(System.in));
        var input = "";
        do {
            state.deal();
            state.printState();
            System.out.println("'h' to hit, 's' to stand");
            var breaker = false;
            do {
                input = buff.readLine();
                switch (input) {
                    case "h":
                        if (!state.hit()) {
                            breaker = true;
                        } else {
                            state.printState();
                        }
                        break;
                    case "s":
                        state.stand();
                        breaker = true;
                        break;
                }
            } while (!breaker);
            state.reset();
            input = buff.readLine();
        } while (!input.equals("q"));
    }
}
