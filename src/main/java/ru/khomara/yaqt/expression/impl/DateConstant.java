package ru.khomara.yaqt.expression.impl;

import org.jetbrains.annotations.NotNull;
import ru.khomara.yaqt.DataSupplier;
import ru.khomara.yaqt.RequestBuilder;
import ru.khomara.yaqt.Value;
import ru.khomara.yaqt.expression.AbstractExpression;
import ru.khomara.yaqt.expression.AbstractType;
import ru.khomara.yaqt.expression.AbstractConstant;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class DateConstant extends AbstractConstant<Date> {

    @Override
    public AbstractType type() {
        return AbstractType.DATE;
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
        return null;
    }

    @Override
    protected @NotNull Value<?> apply0(@NotNull DataSupplier dataSupplier, Stream<Value<?>> args) {
        return null;
    }
}
