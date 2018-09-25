package ru.khomara.yaqt.expression;

import com.google.common.collect.ImmutableList;
import ru.khomara.yaqt.Expression;

import java.util.List;

public abstract class AbstractExpression implements Expression {
    private List<AbstractExpression> arguments;

    protected List<AbstractExpression> arguments() {
        return ImmutableList.copyOf(arguments);
    }

    public final AbstractExpression reduce() {
        return null;
    }

    public final AbstractExpression optimize() {
        return null;
    }

    public final AbstractExpression reduce0() {
        return null;
    }

    public final AbstractExpression optimize0() {
        return null;
    }
}
