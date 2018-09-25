package ru.khomara.yaqt;

import java.util.List;

public interface Value<T extends Comparable<? super T>> {
    Type type();

    List<T> value();
}
