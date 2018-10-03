package ru.khomara.yaqt;

import java.util.List;

public interface Value<T> {
    Type<T> type();

    List<T> value();
}
