package tiles;

import java.awt.*;

public class ObstacleTile extends Tile{
    /**
     * Constructor for TrapTile
     *
     * @param row   row position
     * @param col   col position
     * @param color color of Tile
     */
    public ObstacleTile(int row, int col, Color color) {
        super(row, col, color);
    }
    /**
     * renders tile on the board based on row/col.
     * @param g graphics component
     */
    public void render(Graphics g) {

        int widthOfTile = 40;
        int tileX = (this.col * 50);
        int heightOfTile = 40;
        int tileY = (this.row * 50);

        g.setColor(this.color);
        g.fillOval(tileX, tileY, widthOfTile, heightOfTile);
    }
}
