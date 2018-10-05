package ru.khomara.yaqt;

import org.jetbrains.annotations.NotNull;

public interface Expression {

    /*
     * ATTRIBUTES
     */

    @NotNull
    Type type();

    boolean isSupported(@NotNull RequestBuilder<?> builder);

    /*
     * MUTATION
     */

    @NotNull
    Expression optimize();

    @NotNull
    Expression clone();

    @NotNull
    Expression reduceToSupported(@NotNull RequestBuilder<?> builder);

    @NotNull
    Expression resolve(@NotNull Context context);

    /*
     * TRANSFORMATION
     */

    @NotNull
    <R> R transform(@NotNull RequestBuilder<R> builder);

    @NotNull
    Value<?> apply(@NotNull DataSupplier dataSupplier);

    @NotNull
    String print();
}
