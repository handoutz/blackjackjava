package com.vince.blackjack.casino;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

public class Bank {
    private List<Chip> chips;
    private String name;
    private Consumer<Bank> onValueUpdated;

    public Bank(String name) {
        chips = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Chip> getChips() {
        return chips;
    }

    public int getAmount() {
        return chips.stream()
                .map(Chip::getValue)
                .reduce(0, Integer::sum);
    }

    public void addChip(Chip chip) {
        chips.add(chip);
        valueUpdated();
    }

    public void bankrupt() {
        chips.clear();
        valueUpdated();
    }

    public void addNewChip(ChipValue val) {
        addChip(new Chip(val));
    }

    public void addNewChip(ChipValue val, int ct) {
        for (int i = 0; i < ct; i++) {
            addChip(new Chip(val));
        }
    }

    public boolean removeChip(Chip chip) {
        //if(chips.contains(chip)) chips.removeIf(c -> c.getId() == chip.getId());
        var res = chips.remove(chip);
        valueUpdated();
        return res;
    }

    public void setValueListener(Consumer<Bank> consumer) {
        onValueUpdated = consumer;
    }

    private void valueUpdated() {
        if (onValueUpdated != null) {
            onValueUpdated.accept(this);
        }
    }
}
