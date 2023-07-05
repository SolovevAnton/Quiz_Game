package com.solovev.quiz_game.controllers;

/**
 * Interface to pass objects to the controllers
 * @param <T> type of data to pass
 */

public interface ControllerData<T> {
    void initData(T value);
}
