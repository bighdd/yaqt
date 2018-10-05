package ru.khomara.yaqt.expression;

import com.google.common.collect.ImmutableList;
import ru.khomara.yaqt.Type;
import ru.khomara.yaqt.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 Represents type of expression.
 Supported types:
 <li>String
 <li>Boolean
 <li>Date
 <li>Float
 <li>Integer
 Synthetic types:
 <li>Same
 <li>Any
 */
public class AbstractType<T> implements Type<T> {
    public static final AbstractType<String> STRING = new AbstractType<>("STRING", String.class);
    public static final AbstractType<Boolean> BOOLEAN = new AbstractType<>("BOOLEAN", Boolean.class);
    public static final AbstractType<Date> DATE = new AbstractType<>("DATE", Date.class);
    public static final AbstractType<Double> FLOAT = new AbstractType<>("FLOAT", Double.class);
    public static final AbstractType<Long> INTEGER = new AbstractType<>("INTEGER", Long.class);
    protected static final AbstractType<?> SAME = new AbstractType<>("SAME", null);
    protected static final AbstractType<?> ANY = new AbstractType<>("ANY", null);

    private static final Map<Class<?>, AbstractType<?>> TYPES_MAP = Stream.of(STRING, FLOAT, INTEGER, BOOLEAN, DATE)
            .collect(Collectors.toMap(AbstractType::getClazz, Function.identity()));

    private static class CastFunction<T, R> {
        private final AbstractType<T> fromType;
        private final AbstractType<R> toType;
        private final Function<T, R> castFunction;

        private CastFunction(AbstractType<T> fromType, AbstractType<R> toType, Function<T, R> castFunction) {
            this.fromType = fromType;
            this.toType = toType;
            this.castFunction = castFunction;
        }

        public Function<T, R> getCastFunction() {
            return castFunction;
        }

        private Pair<AbstractType<?>, AbstractType<?>> getKey() {
            return Pair.of(fromType, toType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CastFunction<?, ?> that = (CastFunction<?, ?>) o;
            return Objects.equals(fromType, that.fromType) &&
                    Objects.equals(toType, that.toType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromType, toType);
        }

        public static <T, R> CastFunction<T, R> of(AbstractType<T> fromType, AbstractType<R> toType, Function<T, R> castFunction) {
            return new CastFunction<>(fromType, toType, castFunction);
        }
    }

    private final static Map<Pair<AbstractType<?>, AbstractType<?>>, CastFunction<?, ?>> CAST_FUNCTION_LUT = ImmutableList.<CastFunction<?, ?>>builder()
            .add(CastFunction.of(STRING, STRING, Function.identity()))
            .add(CastFunction.of(BOOLEAN, BOOLEAN, Function.identity()))
            .add(CastFunction.of(DATE, DATE, Function.identity()))
            .add(CastFunction.of(FLOAT, FLOAT, Function.identity()))
            .add(CastFunction.of(INTEGER, INTEGER, Function.identity()))

            .add(CastFunction.of(BOOLEAN, STRING, Object::toString))
            .add(CastFunction.of(DATE, STRING, Date::toString))
            .add(CastFunction.of(FLOAT, STRING, Object::toString))
            .add(CastFunction.of(INTEGER, STRING, Object::toString))

            .add(CastFunction.of(STRING, BOOLEAN, Boolean::valueOf))
            .add(CastFunction.of(STRING, FLOAT, Double::valueOf))
            .add(CastFunction.of(STRING, INTEGER, Long::valueOf))

            .add(CastFunction.of(DATE, INTEGER, Date::getTime))
            .add(CastFunction.of(INTEGER, DATE, Date::new))
            .add(CastFunction.of(FLOAT, INTEGER, Double::longValue))
            .add(CastFunction.of(INTEGER, FLOAT, Long::doubleValue))
            .build()
            .stream()
            .collect(Collectors.toMap(CastFunction::getKey, Function.identity()));

    private final String name;
    private final Class<T> clazz;

    private AbstractType(String name, Class<T> clazz) {
        this.name = name.toUpperCase();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public <R> Function<T, R> asCast(Type<R> that) {
        return Optional.ofNullable(CAST_FUNCTION_LUT.get(Pair.of(this, that)))
                .map(CastFunction::getCastFunction)
                .map(function -> (Function<T, R>) function)
                .orElseThrow(() -> new IllegalArgumentException("Can't cast: from=" + this + ", to=" + that));
    }

    public boolean castable(Type<?> that) {
        return this == ANY || that == ANY || CAST_FUNCTION_LUT.containsKey(Pair.of(this, that));
    }

    public boolean isSame() {
        return this == SAME;
    }

    public boolean isSpecific() {
        return this != ANY;
    }

    public static <T> AbstractType<T> get(Class<T> clazz) {
        return Optional.ofNullable(TYPES_MAP.get(clazz))
                .map(type -> (AbstractType<T>) type)
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: class=" + clazz.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type<?> that = (Type<?>) o;
        return this == ANY || that == ANY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}