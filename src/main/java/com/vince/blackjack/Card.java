package com.vince.blackjack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Card {
    private Suite suite;
    private String name;
    private int value;
    @Builder.Default
    private boolean faceUp = true;

    @Override
    public String toString() {
        return String.format("%s:%s", suite.toString(), name);
    }

}
