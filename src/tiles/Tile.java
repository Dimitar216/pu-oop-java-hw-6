package tiles;

import java.awt.*;

public class Tile {
    private int row;
    private int col;
    private final Color color;


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
}