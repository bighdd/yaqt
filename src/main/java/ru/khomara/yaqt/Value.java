package ru.khomara.yaqt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Value<T> {
    @NotNull
    Type<T> type();

    @NotNull
    List<T> value();
}
