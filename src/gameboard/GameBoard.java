package gameboard;

import tiles.FoodTile;
import tiles.SnakeTile;
import tiles.Tile;
import tiles.ObstacleTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard extends JFrame implements MouseListener {
    private Tile[][] tileCollection = new Tile[8][16];
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
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardCoordinates(e.getY());
        int col = this.getBoardCoordinates(e.getX());
        System.out.println(row);
        System.out.println(col);
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
    private int getBoardCoordinates(int coordinates){
        return  coordinates/50;
    }

    private boolean hasBoardTile(int row,int col){
        return this.getBoardTile(row,col) !=null;
    }

    private Tile getBoardTile(int row, int col){
        return this.tileCollection[row][col];
    }
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
    private void snakeTileSetUp(){
        SnakeTile tile = new SnakeTile(4,7,Color.BLACK);
        tileCollection[4][7]= tile;
    }
}
