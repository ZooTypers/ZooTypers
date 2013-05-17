package com.example.zootypers;

public class States {

    public enum difficulty {
        EASY, MEDIUM, HARD 
    };

    public enum update {
        HIGHLIGHT, WRONG_LETTER, FINISHED_WORD, OPPONENT_WORD, OPPONENT_SCORE, CONNECTION_ERROR, NO_OPPONENT, REAL_ERROR
    };
}
