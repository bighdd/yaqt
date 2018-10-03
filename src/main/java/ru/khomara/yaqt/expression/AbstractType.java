package ru.khomara.yaqt.expression;

import com.google.common.collect.ImmutableList;
import ru.khomara.yaqt.Type;
import ru.khomara.yaqt.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractType<T> implements Type<T> {
    public static final AbstractType<String> STRING = new AbstractType<>("STRING", String.class);
    public static final AbstractType<Boolean> BOOLEAN = new AbstractType<>("BOOLEAN", Boolean.class);
    public static final AbstractType<Date> DATE = new AbstractType<>("DATE", Date.class);
    public static final AbstractType<Double> FLOAT = new AbstractType<>("FLOAT", Double.class);
    public static final AbstractType<Long> INTEGER = new AbstractType<>("INTEGER", Long.class);

    private static final Map<Class<?>, Type<?>> TYPES_MAP = Stream.of(STRING, FLOAT, INTEGER, BOOLEAN, DATE)
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
        this.name = name;
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
        return CAST_FUNCTION_LUT.containsKey(Pair.of(this, that));
    }

    public static <T> Type<T> get(Class<T> clazz) {
        return Optional.ofNullable(TYPES_MAP.get(clazz))
                .map(type -> (Type<T>) type)
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: class=" + clazz.getName()));
    }

    @Override
    public String toString() {
        return name;
    }
}