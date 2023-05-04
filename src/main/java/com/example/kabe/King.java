package com.example.kabe;
public class King extends Piece {
    private char symbol;

    public King(boolean color) {
        super(color);
        if (super.isColor())
            this.symbol = Board.manWhiteSymbol;
        else this.symbol = Board.manBlackSymbol;
    }
}

