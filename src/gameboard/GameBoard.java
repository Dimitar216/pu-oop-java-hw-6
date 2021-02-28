package gameboard;

import tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoard extends JFrame implements MouseListener {
    public GameBoard(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,400);
        this.setVisible(true);
    }
    /**
     * method which paints all the tiles/
     * @param g graphics component
     */
    @Override
    public void paint(Graphics g){
        backgroundSetUp(g);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

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
}
