package com.solovev.quiz_game.model.enums;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum Type {
    @JsonAlias("multiple")
    MULTIPLE,
    @JsonAlias("boolean")
    BOOLEAN
}
