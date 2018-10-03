package ru.khomara.yaqt.expression;

public class UnaryExpression extends AbstractExpression {
    enum UnaryOperation {
        PLUS,
        MINUS,
        NOT

        UnaryOperation(List<Type>)
    }
}
