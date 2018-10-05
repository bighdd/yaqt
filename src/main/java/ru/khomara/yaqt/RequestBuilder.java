package ru.khomara.yaqt;

import org.jetbrains.annotations.NotNull;
import ru.khomara.yaqt.expression.impl.*;

public interface RequestBuilder<R> {
    @NotNull
    R build(@NotNull IntegerConstant expr);
    @NotNull
    R build(@NotNull StringConstant expr);
    @NotNull
    R build(@NotNull FloatConstant expr);
    @NotNull
    R build(@NotNull DateConstant expr);
    @NotNull
    R build(@NotNull BinaryExpression expr);
    @NotNull
    R build(@NotNull UnaryExpression expr);
    @NotNull
    R build(@NotNull Function expr);

    boolean isSupported(@NotNull IntegerConstant expr);
    boolean isSupported(@NotNull StringConstant expr);
    boolean isSupported(@NotNull FloatConstant expr);
    boolean isSupported(@NotNull DateConstant expr);
    boolean isSupported(@NotNull BinaryExpression expr);
    boolean isSupported(@NotNull UnaryExpression expr);
    boolean isSupported(@NotNull Function expr);
}
