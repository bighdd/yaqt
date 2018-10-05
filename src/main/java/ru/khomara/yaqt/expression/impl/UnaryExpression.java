package ru.khomara.yaqt.expression.impl;

import ru.khomara.yaqt.expression.AbstractExpression;

public class UnaryExpression extends AbstractExpression {
    enum UnaryOperation {
        PLUS,
        MINUS,
        NOT

        UnaryOperation(List<Type>)
    }
}
