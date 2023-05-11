package com.example.kabe;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int boardSize;
    private Square[][] board; // teine pool konstruktorisse

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.board = setupBoard();
        this.legalMoves = new ArrayList<>();
    }

    List<int[]> legalMoves;
    public static char manWhiteSymbol = 'O'; //ajutine
    public static char manBlackSymbol = 'X';

    //public static char kingWhiteSymbol = '☼';
    //public static char kingBlackSymbol = '✪';

    private static final String[][] boardSetup10x10 = {
            {"", "bM", "", "bM", "", "bM", "", "bM", "", "bM"},
            {"bM", "", "bM", "", "bM", "", "bM", "", "bM", ""},
            {"", "bM", "", "bM", "", "bM", "", "bM", "", "bM"},
            {"bM", "", "bM", "", "bM", "", "bM", "", "bM", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "wM", "", "wM", "", "wM", "", "wM", "", "wM"},
            {"wM", "", "wM", "", "wM", "", "wM", "", "wM", ""},
            {"", "wM", "", "wM", "", "wM", "", "wM", "", "wM"},
            {"wM", "", "wM", "", "wM", "", "wM", "", "wM", ""}};


    public Square[][] setupBoard() {//kasutame boardSetup10x10, et luua laud nuppudega
        Square[][] board = new Square[boardSize][boardSize];
        if (boardSize == 10) {
            for (int i = 0; i < boardSetup10x10.length; i++) {
                for (int j = 0; j < boardSetup10x10[i].length; j++) {
                    switch (boardSetup10x10[i][j]) {
                        case "" -> board[i][j] = new Square(true, null);
                        case "bM" -> board[i][j] = new Square(false, new Man(false));
                        case "wM" -> board[i][j] = new Square(false, new Man(true));
                    }
                }
            }
        }
        return board;
    }


    public void printBoard() {
        System.out.println("       A    B   C   D   E   F   G   H   I   J \n" +
                "      -----------------------------------------");
        for (int i = 0; i < boardSize; i++) {
            String row = "   " + (boardSize - i) + "|";
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].isEmpty()) {
                    row += "   |";
                    continue;
                } else {
                    if (board[i][j].getContains().isColor())
                        row += " " + manWhiteSymbol + " |";
                    else if (!board[i][j].getContains().isColor())
                        row += " " + manBlackSymbol + " |";
                }
            }
            System.out.println(row);
            System.out.println("    -----------------------------------------  ");
        }
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

    /* kasutamata
            public void alterBoard(int x, int y, Square sq){
                board[x][y] = sq;
            }

         */
    public void generateLegalMoves(boolean whosMove) {
        legalMoves.clear();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Square checkSquare = board[i][j];
                if (checkSquare.isEmpty()) continue;
                if (whosMove == true) { // valge kontroll
                    if (checkSquare.getContains().isColor() == whosMove) {
                        if (i != 0 && j != 0) {
                            if (board[i - 1][j - 1].isEmpty())
                                legalMoves.add(new int[]{i, j, i - 1, j - 1}); // TODO kontroll
                            else if (board[i - 1][j - 1].getContains().isColor() != whosMove) {
                                if (i != 1 && j != 1 && board[i - 2][j - 2].isEmpty())
                                    legalMoves.add(new int[]{i, j, i - 2, j - 2});
                            }
                        }

                        if (i != 0 && j != boardSize - 1) {
                            if (board[i - 1][j + 1].isEmpty()) legalMoves.add(new int[]{i, j, i - 1, j + 1});
                            else if (board[i - 1][j + 1].getContains().isColor() != whosMove) {
                                if (i != 1 && j != boardSize - 2 && board[i - 2][j + 2].isEmpty())
                                    legalMoves.add(new int[]{i, j, i - 2, j + 2});
                            }
                        }
                    }
                } else { // must
                    if (checkSquare.getContains().isColor() == whosMove) {
                        if (i != boardSize - 1 && j != boardSize - 1) {
                            if (board[i + 1][j + 1].isEmpty())
                                legalMoves.add(new int[]{i, j, i + 1, j + 1});
                            else if (board[i + 1][j + 1].getContains().isColor() != whosMove) {
                                if (i != boardSize - 2 && j != boardSize - 2 && board[i + 2][j + 2].isEmpty())
                                    legalMoves.add(new int[]{i, j, i + 2, j + 2});
                            }
                        }

                        if (i != boardSize - 1 && j != 0) {
                            if (board[i + 1][j - 1].isEmpty()) legalMoves.add(new int[]{i, j, i + 1, j - 1});
                            else if (board[i + 1][j - 1].getContains().isColor() != whosMove) {
                                if (i != boardSize - 2 && j != 1 && board[i + 2][j - 2].isEmpty())
                                    legalMoves.add(new int[]{i, j, i + 2, j - 2});
                            }
                        }
                    }
                }
            }
        }
        boolean[] captures = new boolean[legalMoves.size()];
        boolean includesCaptures = false;
        for (int i = 0; i < legalMoves.size(); i++) {
            if (Math.abs(legalMoves.get(i)[2] - legalMoves.get(i)[0]) == 2) {
                captures[i] = true;
                includesCaptures = true;
            }
        }
        if (includesCaptures) {
            for (int i = 0; i < legalMoves.size(); i++) {
                if (!captures[i])
                    legalMoves.set(i, new int[]{});
            }
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public void move(int[] move) {
        int startI = move[0];
        int startJ = move[1];
        int endI = move[2];
        int endJ = move[3];

        Piece movingPiece = board[startI][startJ].getContains();

        if (Math.abs(startJ - endJ) == 2) {
            board[endI][endJ].setSquarePiece(movingPiece);
            board[startI][startJ].clearSquare();
            board[startI + ((endI - startI) / 2)][startJ + ((endJ - startJ) / 2)].clearSquare();
        } else {
            board[endI][endJ].setSquarePiece(movingPiece);
            board[startI][startJ].clearSquare();
        }
    }

    public boolean isLegalMove(int[] move) {
        for (int[] legalMove : legalMoves) {
            if (Arrays.equals(legalMove, move))
                return true;
        }
        return false;
    }



}
