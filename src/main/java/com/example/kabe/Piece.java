package com.example.kabe;


public class Piece{
    public String symbol;
    private boolean color; // 1-valge 0-must

    public Piece(boolean color) {
        this.color = color;
    }

    public boolean isColor() {
        return color;
    }
}
