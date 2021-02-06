package com.vince.blackjack.casino;


public enum ChipValue {
    ONE("One", 1),
    FIVE("Five", 5),
    TEN("Ten", 10),
    TWENTY_FIVE("Twenty-Five", 25),
    FIFTY("Fifty", 50),
    ONE_HUNDRED("One Hundred", 100);

    public String name;
    public int value;

    ChipValue(String n, int val) {
        name = n;
        value = val;
    }
}