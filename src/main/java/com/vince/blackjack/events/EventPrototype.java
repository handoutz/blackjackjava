package com.vince.blackjack.events;

import java.util.Objects;

@FunctionalInterface
public interface EventPrototype<T> {
    void accept(T t);

    default java.util.function.Consumer<T> andThen(java.util.function.Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}

