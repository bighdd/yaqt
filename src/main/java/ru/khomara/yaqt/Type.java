package ru.khomara.yaqt;

import java.util.function.Function;

public interface Type<T> {
    String getName();

    <R> Function<T, R> asCast(Type<R> that);

    boolean castable(Type<?> that);
}
