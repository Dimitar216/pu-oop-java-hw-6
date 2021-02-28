package gameboard;

import tiles.FoodTile;
import tiles.SnakeTile;
import tiles.Tile;
import tiles.ObstacleTile;
import ui.Modal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard extends JFrame implements MouseListener {
    private final Tile[][] tileCollection = new Tile[8][16];
    private Tile selectedSnake;

    /**
     * Game board constructor.
     */
    public GameBoard(){
        snakeTileSetUp();
        foodTileSetUp();
        trapTileSetUp();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,400);
        this.setVisible(true);
        this.addMouseListener(this);
    }
    /**
     * method which paints all the tiles/
     * @param g graphics component
     */
    @Override
    public void paint(Graphics g){
        backgroundSetUp(g);
        tileRenderer(g);
    }
    /**
     * Method where movement of the snake is realized through mouse input.
     * @param e mouse event listener.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardCoordinates(e.getY());
        int col = this.getBoardCoordinates(e.getX());

        if(this.selectedSnake !=null){
            Tile tile = this.selectedSnake;
            isSnakeMoveValid(row,col,tile);
            return;
        }

        if (this.hasBoardSnakeTile(row, col)) {
            this.selectedSnake = this.getBoardTile(row, col);
        } else {
            Modal.render(this,"Warning!","You can select only the snake which is the black circle.");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Sets up background of game board.
     * @param g graphics component
     */
    private void backgroundSetUp(Graphics g){
        for(int row = 0; row<8;row++){
            for(int col = 0; col < 16; col++ ){
                Tile tile;
                if(row%2 == 0 && col%2 == 0){
                    tile = new Tile(row, col, Color.LIGHT_GRAY);
                }else if(row%2 == 1 && col%2 == 1){
                    tile = new Tile(row, col, Color.LIGHT_GRAY);
                } else {
                    tile = new Tile(row, col, Color.GRAY);
                }
                tile.render(g);
            }
        }
    }

    /**
     * Method that sets up traps randomly.
     */
    private void trapTileSetUp(){
        for(int i = 0; i<8;i++) {
                int randomRow = ThreadLocalRandom.current().nextInt(0,8);
                int randomCol = ThreadLocalRandom.current().nextInt(0,16);
                if(tileCollection[randomRow][randomCol] == null){
                    ObstacleTile tile = new ObstacleTile(randomRow,randomCol,Color.RED);
                    tileCollection[randomRow][randomCol]= tile;
                } else {
                    i--;
                }
        }
    }

    /**
     * Method that sets up food randomly.
     */
    private void foodTileSetUp(){
        for(int i = 0; i<8;i++) {
            int randomRow = ThreadLocalRandom.current().nextInt(0,8);
            int randomCol = ThreadLocalRandom.current().nextInt(0,16);
            if(tileCollection[randomRow][randomCol] == null){
                FoodTile tile = new FoodTile(randomRow,randomCol,Color.GREEN);
                tileCollection[randomRow][randomCol]= tile;
            } else {
                i--;
            }
        }
    }
    /**
     * Method which gets coordinates based on inputted X/Y and returns row/col
     * @param coordinates X or Y coordinate.
     * @return row or col coordinate.
     */
    private int getBoardCoordinates(int coordinates){
        return  coordinates/50;
    }

    /**
     * Method which checks if the tile exists.
     * @param row row of the tile.
     * @param col col of the tile.
     * @return true if it exists,false if not.
     */
    private boolean hasBoardTile(int row,int col){
        return this.getBoardTile(row,col) != null;
    }

    /**
     * Gets tile from array with inputted coordinates
     * @param row row of tile.
     * @param col col of tile.
     * @return tile
     */
    private Tile getBoardTile(int row, int col){
        return this.tileCollection[row][col];
    }

    /**
     * Method which checks if there is tile in the array and renders it if there is
     * @param g graphics component.
     */
    private void tileRenderer(Graphics g){
        for(int row = 0; row<8;row++){
            for(int col = 0; col < 16; col++ ){
                if(this.hasBoardTile(row,col)){
                    Tile tile = getBoardTile(row,col);
                    tile.render(g);
                }
            }
        }
    }

    /**
     * Method which sets up the snake tile.
     */
    private void snakeTileSetUp(){
        SnakeTile tile = new SnakeTile(4,7,Color.BLACK);
        tileCollection[4][7]= tile;
    }

    /**
     * Method which checks if the snake tile exists.
     * @param row row of the snake tile.
     * @param col col of the snake tile.
     * @return true if it exists,false if not.
     */
    private boolean hasBoardSnakeTile(int row,int col){
        if(getBoardTile(row,col) != null) {
            Tile tile = getBoardTile(row, col);
            return tile.getColor().equals(Color.BLACK);
        } else {
            return false;
        }
    }

    /**
     * Method where the movement of the snake is realized.
     * @param row row of the snake.
     * @param col col of the snake.
     * @param guard snake object.
     */
    private void isSnakeMoveValid(int row, int col, Tile snake){
        if(snake.isMoveValid(row,col,this.tileCollection)){
            if(this.tileCollection[row][col] == null) {
                int initialRow = snake.getRow();
                int initialCol = snake.getCol();

                snake.move(row, col);
                this.tileCollection[snake.getRow()][snake.getCol()] = this.selectedSnake;
                this.tileCollection[initialRow][initialCol] = null;
                this.selectedSnake = null;
                this.repaint();
            } else{
                Modal.render(this,"Warning!","Invalid move");
            }
        } else {
            Modal.render(this,"Warning!","Invalid move");
        }
    }
}
