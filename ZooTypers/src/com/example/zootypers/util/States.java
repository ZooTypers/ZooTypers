package com.example.zootypers.util;

public class States {

    public enum difficulty {
        EASY, MEDIUM, HARD 
    };

    public enum update {
        HIGHLIGHT, WRONG_LETTER, FINISHED_WORD, OPPONENT_WORD, OPPONENT_SCORE
    };
    
    public enum error {
    	CONNECTION, INTERNAL, NOOPPONENT
    };
}
