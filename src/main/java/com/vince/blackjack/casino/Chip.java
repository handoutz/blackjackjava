package com.vince.blackjack.casino;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class Chip {
    private UUID id;
    private int value;

    public Chip(int val) {
        value = val;
        id = UUID.randomUUID();
    }

    public Chip(ChipValue val) {
        this(val.value);
    }
}
