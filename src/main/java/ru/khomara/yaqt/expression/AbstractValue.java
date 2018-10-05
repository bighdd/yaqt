package ru.khomara.yaqt.expression;

import ru.khomara.yaqt.Type;
import ru.khomara.yaqt.Value;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ru.khomara.yaqt.expression.AbstractType.ANY;
import static ru.khomara.yaqt.expression.AbstractType.DATE;

public class AbstractValue<T> implements Value<T> {

    private final AbstractType<T> type;
    private final List<T> value;

    private AbstractValue(List<T> value, AbstractType<T> type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractValue<?> that = (AbstractValue<?>) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "'" + value + "'{" + type +"}'";
    }

    public String print() {
        if (DATE == type) {
            return "fromMillis(" + value + ")";
        }
    }

    public static <T> AbstractValue<T> singleton(T value) {
        return new AbstractValue<T>(Collections.singletonList(value), (AbstractType<T>) AbstractType.get(value.getClass()));
    }

    public static <T> AbstractValue<T> complex(List<T> value, Class<T> clazz) {
        return new AbstractValue<T>(value, AbstractType.get(clazz));
    }

    public static <T> AbstractValue<T> empty() {
        return new AbstractValue<T>(Collections.emptyList(), ANY);
    }

    public static AbstractValue<?> nall() {
        return new AbstractValue<Object>((Object)null, ANY);
    }
}
