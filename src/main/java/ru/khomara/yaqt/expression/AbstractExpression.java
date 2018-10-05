package ru.khomara.yaqt.expression;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import ru.khomara.yaqt.*;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractExpression implements Expression {
    @NotNull
    private List<AbstractExpression> arguments;

    @NotNull
    protected List<AbstractExpression> arguments() {
        return ImmutableList.copyOf(arguments);
    }

    @Override
    @NotNull
    public abstract AbstractType<?> type();

    public final boolean isConstant() {
        return allMatch(AbstractExpression::isConstant0);
    }

    @Override
    public final boolean isSupported(@NotNull RequestBuilder<?> builder) {
        return allMatch(expr -> expr.isSupported0(builder));
    }

    @Override
    @NotNull
    public final Expression optimize() {
        return transform(AbstractExpression::optimize0).get(0);
    }

    @Override
    @NotNull
    public abstract AbstractExpression clone();

    @Override
    @NotNull
    public final Expression reduceToSupported(@NotNull RequestBuilder<?> builder) {
        return transform(expr -> expr.reduceToSupported0(builder)).get(0);
    }

    @Override
    @NotNull
    public final Expression resolve(@NotNull Context context) {
        return transform(expr -> expr.resolve0(context)).get(0);
    }

    @Override
    @NotNull
    public final <R> R transform(@NotNull RequestBuilder<R> builder) {
        return accumulate((expr, args) -> expr.transform0(builder, args));
    }

    @Override
    @NotNull
    public final Value<?> apply(@NotNull DataSupplier dataSupplier) {
        return accumulate((expr, args) -> expr.apply0(dataSupplier, args));
    }

    @Override
    @NotNull
    public String print() {
        return null;
    }

    /*
     * TRAVERSE
     */

    private boolean allMatch(Predicate<AbstractExpression> predicate) {
       return accumulate((expr, args) -> args.allMatch(Boolean.TRUE::equals) && predicate.test(expr));
    }

    @NotNull
    private List<AbstractExpression> transform(@NotNull Function<AbstractExpression, List<AbstractExpression>> transformer) {
        arguments = arguments().stream().map(argument -> argument.transform(transformer)).flatMap(List::stream).collect(Collectors.toList());
        return transformer.apply(this);
    }

    @NotNull
    private <R> R accumulate(@NotNull BiFunction<AbstractExpression, Stream<R>, R> accumulator) {
        return accumulator.apply(this, arguments().stream().map(argument -> argument.accumulate(accumulator)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractExpression that = (AbstractExpression) o;
        return Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arguments);
    }

    /*
     * PROTECTED
     */

    protected abstract boolean isConstant0();

    protected abstract boolean isSupported0(@NotNull RequestBuilder<?> builder);

    @NotNull
    protected abstract List<AbstractExpression> optimize0();

    @NotNull
    protected abstract List<AbstractExpression> reduceToSupported0(RequestBuilder<?> builder);

    @NotNull
    protected abstract List<AbstractExpression> resolve0(@NotNull Context context);

    @NotNull
    protected abstract <R> R transform0(@NotNull RequestBuilder<R> builder, Stream<R> args);

    @NotNull
    protected abstract Value<?> apply0(@NotNull DataSupplier dataSupplier, Stream<Value<?>> args);

    @NotNull
    protected List<AbstractExpression> cloneArguments() {
        return arguments.stream().map(AbstractExpression::clone).collect(Collectors.toList());
    }
}
