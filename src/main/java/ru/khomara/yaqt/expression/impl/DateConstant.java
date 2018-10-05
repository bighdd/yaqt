package ru.khomara.yaqt.expression.impl;

import ru.khomara.yaqt.expression.AbstractType;
import ru.khomara.yaqt.expression.AbstractConstant;

import java.util.Date;

public class DateConstant extends AbstractConstant<Date> {

    @Override
    public AbstractType type() {
        return null;
    }
}
