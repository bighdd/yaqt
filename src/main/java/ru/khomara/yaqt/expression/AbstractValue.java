package ru.khomara.yaqt.expression;

import ru.khomara.yaqt.Type;
import ru.khomara.yaqt.Value;

import java.util.Collections;
import java.util.List;

public class AbstractValue<T> implements Value<T> {

    private final Type<T> type;
    private final List<T> value;

    private AbstractValue(Type<T> type, List<T> value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Type<T> type() {
        return type;
    }

    @Override
    public List<T> value() {
        return value;
    }

    public static <T> AbstractValue<T> singleton(T value) {
        return new AbstractValue<T>(AbstractType.get((Class<T>) value.getClass()), Collections.singletonList(value));
    }

    public static <T> AbstractValue<T> complex(List<T> value, Class<T> clazz) {
        return new AbstractValue<T>(AbstractType.get(clazz), value);
    }
}
