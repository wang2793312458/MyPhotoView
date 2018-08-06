package com.example.myphotoview;

import android.support.annotation.NonNull;

public class CompatDecoderFactory<T> implements DecoderFactory<T> {
    private Class<? extends T> clazz;

    public CompatDecoderFactory(@NonNull Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    public T make() throws IllegalAccessException, InstantiationException {
        return this.clazz.newInstance();
    }
}
