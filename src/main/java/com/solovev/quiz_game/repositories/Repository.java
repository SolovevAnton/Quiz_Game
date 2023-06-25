package com.solovev.quiz_game.repositories;

import java.io.File;

public interface Repository<T> {
    T takeData();
    void save(File pathToFile);
}
