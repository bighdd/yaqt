package ru.khomara.yaqt.expression;

public abstract class AbstractConstant<T> extends AbstractExpression {

    private T value;

    public final T value() {
        return value;
    }

}
