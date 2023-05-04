package com.example.kabe;
public class Man extends Piece{
    private static char whiteSymbol = '◯';
    private final static char blackSymbol = '⬤';

    public char symbol;
    public Man(boolean color) {
        super(color);
        if (super.isColor())
            this.symbol = Board.manWhiteSymbol;
        else this.symbol = Board.manBlackSymbol;
    }

}
