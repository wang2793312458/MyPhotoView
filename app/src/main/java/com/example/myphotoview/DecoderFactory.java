package com.example.myphotoview;

public interface DecoderFactory<T> {
    T make() throws IllegalAccessException, InstantiationException;
}

