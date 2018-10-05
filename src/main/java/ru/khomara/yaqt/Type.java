package ru.khomara.yaqt;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface Type<T> {
    @NotNull
    String getName();

    @NotNull
    <R> Function<T, R> asCast(@NotNull Type<R> that);

    boolean castable(@NotNull Type<?> that);
}
