package ru.khomara.yaqt.expression.impl;

import ru.khomara.yaqt.expression.AbstractType;
import ru.khomara.yaqt.expression.AbstractConstant;

public class StringConstant extends AbstractConstant<String> {
    @Override
    public AbstractType type() {
        return null;
    }
}
