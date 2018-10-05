package ru.khomara.yaqt.expression.impl;

import org.jetbrains.annotations.NotNull;
import ru.khomara.yaqt.DataSupplier;
import ru.khomara.yaqt.RequestBuilder;
import ru.khomara.yaqt.Value;
import ru.khomara.yaqt.expression.AbstractExpression;
import ru.khomara.yaqt.expression.AbstractType;
import ru.khomara.yaqt.expression.AbstractConstant;
import ru.khomara.yaqt.expression.AbstractValue;

import java.util.List;
import java.util.stream.Stream;

public class StringConstant extends AbstractConstant<String> {
    @Override
    public AbstractType type() {
        return AbstractType.STRING;
    }

    @Override
    public @NotNull AbstractExpression clone() {
        return null;
    }

    @Override
    protected boolean isSupported0(@NotNull RequestBuilder<?> builder) {
        return builder.isSupported(this);
    }

    @Override
    protected @NotNull List<AbstractExpression> reduceToSupported0(RequestBuilder<?> builder) {
        return null;
    }

    @Override
    protected <R> @NotNull R transform0(@NotNull RequestBuilder<R> builder, Stream<R> args) {
        return builder.build(this);
    }

    @Override
    protected @NotNull Value<?> apply0(@NotNull DataSupplier dataSupplier, Stream<Value<?>> args) {
        return AbstractValue.singleton(value());
    }
}
