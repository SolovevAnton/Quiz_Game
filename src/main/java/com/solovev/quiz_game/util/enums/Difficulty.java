package com.solovev.quiz_game.util.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Difficulty {
    @JsonProperty("easy")
    EASY,
    @JsonProperty("medium")
    MEDIUM,
    @JsonProperty("hard")
    HARD
}
