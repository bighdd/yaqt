package ru.khomara.yaqt;

public interface Expression {

    Type type();

    Expression reduce();

    Expression optimize();
}
