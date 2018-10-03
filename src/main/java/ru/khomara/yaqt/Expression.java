package ru.khomara.yaqt;

import ru.khomara.yaqt.expression.AbstractType;

public interface Expression {

    AbstractType type();

    Expression reduce();

    Expression optimize();
}
