package tiles;

import java.awt.*;

public class Tile {
    protected int row;
    protected int col;
    protected final Color color;


    /**
     * Constructor for Tile
     * @param row row position
     * @param col col position
     * @param color color of Tile
     */
    public Tile(int row, int col,Color color){
        this.row          = row;
        this.col          = col;
        this.color = color;
    }
    /**
     * renders tile on the board based on row/col.
     * @param g graphics component
     */
    public void render(Graphics g) {

        int widthOfTile = 50;
        int tileX = (this.col * widthOfTile);
        int heightOfTile = 50;
        int tileY = (this.row * heightOfTile);

        g.setColor(this.color);
        g.fillRect(tileX, tileY, widthOfTile, heightOfTile);
    }

    public Color getColor() {
        return color;
    }
    /**
     * Method which changes the row and col of the figure.
     * @param row new row of the figure.
     * @param col new col of the figure.
     */
    public void move(int row,int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public boolean isMoveValid(int moveRow, int moveCol,Tile[][] figures) {
        int rowCoefficient =  Math.abs(moveRow-this.row);
        int colCoefficient =  Math.abs(moveCol - this.col);

        return rowCoefficient == 0 && colCoefficient == 1 || rowCoefficient == 1 && colCoefficient == 0;
    }
}
