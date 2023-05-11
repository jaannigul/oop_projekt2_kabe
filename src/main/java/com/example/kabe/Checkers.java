package com.example.kabe;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class Checkers extends Application {
    private static final int BOARD_SIZE = 10;
    private static final int TILE_SIZE = 50;
    private Circle[][] pieces = new Circle[BOARD_SIZE][BOARD_SIZE];
    private int sourceRow;
    private int sourceCol;
    private StackPane sourceTile;
    private double offsetX = 0;
    private double offsetY = 0;
    GridPane gridPane = new GridPane();
    Game game = new Game(BOARD_SIZE);
    private int targetRow;
    private int targetCol;


    @Override
    public void start(Stage primaryStage){
        Pane pane = new Pane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        Board board = game.getGameBoard();

        // kabelaua ruudustik
        drawBoard(board);

        //System.out.println(Arrays.deepToString(game.getGameBoard().getBoard()));//debug
        //UItoBoard();
        //System.out.println(Arrays.deepToString(game.getGameBoard().getBoard()));//debug
        pane.getChildren().add(gridPane);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkers");
        primaryStage.show();
    }

    private void addMouseHandlers(Circle piece) {//TODO

        piece.setOnMousePressed(e -> {
            sourceTile = (StackPane) piece.getParent();
            sourceRow = GridPane.getRowIndex(sourceTile);
            sourceCol = GridPane.getColumnIndex(sourceTile);
            offsetX = e.getSceneX();
            offsetY = e.getSceneY();
        });


        piece.setOnMouseDragged(e -> {
            // liigutab nuppu
            double deltaX = e.getSceneX() - offsetX;
            double deltaY = e.getSceneY() - offsetY;
            if (sourceRow != -1 && sourceCol != -1) {
                piece.setTranslateX(deltaX);
                piece.setTranslateY(deltaY);
                int newRow = (int) ((piece.getTranslateY() + TILE_SIZE / 2) / TILE_SIZE);
                int newCol = (int) ((piece.getTranslateX() + TILE_SIZE / 2) / TILE_SIZE);
                if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE) {
                    targetRow = newRow;
                    targetCol = newCol;
                }
            }
        });

        piece.setOnMouseReleased(e -> {
            // leiab nupu sihtkoha
            int targetCol = (int) (e.getSceneX() / (TILE_SIZE));
            int targetRow = (int) (e.getSceneY() / (TILE_SIZE));
            StackPane targetTile = (StackPane) piece.getParent();

            //int targetRow = GridPane.getRowIndex(targetTile);
            //int targetCol = GridPane.getColumnIndex(targetTile);

            // käigu kontrollimine
            int[] move = {sourceRow, sourceCol, targetRow, targetCol};
            System.out.println(Integer.toString(sourceRow) + " " +
                    Integer.toString(sourceCol) + " " +
                    Integer.toString((targetRow)) + " " +
                    Integer.toString((targetCol)));
            game.playPlyGUI(sourceRow, sourceCol, targetRow, targetCol);
            drawBoard(game.getGameBoard());

        });
    }




    /**
     * Võtab kasutajaliidese seisu ja paneb selle klassi Board seisu.
     */


    public void drawBoard(Board board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, ((row + col) % 2 == 0) ? Color.BEIGE : Color.DARKGRAY);
                StackPane tilePane = new StackPane();
                tilePane.getChildren().add(tile);
                gridPane.add(tilePane, col, row);

                if (board.getBoard()[row][col].isEmpty()) {
                    pieces[row][col] = null;
                    continue;
                }
                if (board.getBoard()[row][col].getContains().isColor()) {
                    Circle piece = new Circle(TILE_SIZE / 2 - 5, Color.BLACK);
                    pieces[row][col] = piece;
                    tilePane.getChildren().add(piece);
                    addMouseHandlers(piece);
                }
                if (!board.getBoard()[row][col].getContains().isColor()) {
                    Circle piece = new Circle(TILE_SIZE / 2 - 5, Color.RED);
                    pieces[row][col] = piece;
                    tilePane.getChildren().add(piece);
                }
            }
        }
    }

}
