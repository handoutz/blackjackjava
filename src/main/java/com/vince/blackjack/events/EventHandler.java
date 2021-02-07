package com.vince.blackjack.events;

import java.util.ArrayList;
import java.util.List;

public class EventHandler<T> {
    private List<EventPrototype<T>> registered;

    public EventHandler() {
        registered = new ArrayList<>();
    }

    public void subscribe(EventPrototype<T> cb) {
        registered.add(cb);
    }

    public void accept(T param){
        trigger(param);
    }

    public void trigger(T param) {
        for (var a : registered)
            a.accept(param);
    }
}
