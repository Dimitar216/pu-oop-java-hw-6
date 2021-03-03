package gameboard;

import CustomArrayList.CustomArrayList;
import tiles.FoodTile;
import tiles.SnakeTile;
import tiles.Tile;
import tiles.ObstacleTile;
import ui.Modal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard extends JFrame implements MouseListener,ActionListener {
    private final Tile[][] tileCollection = new Tile[10][18];
    private Tile selectedSnake;
    private int score = 0;
    private final CustomArrayList customArrayList = new CustomArrayList();
    private Tile snakeUpdater;
    private static boolean isGameInPlay = false;
    private static boolean isGameUnPaused = true;
    private boolean gameHasNotEnded = true;
    JPanel buttonPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton restartButton = new JButton("Restart");
    JButton pauseButton = new JButton("Pause");

    /**
     * Game board constructor.
     */
    public GameBoard(){
        snakeTileSetUp();
        foodTileSetUp();
        trapTileSetUp();
        buttonSetUp();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900,550);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    /**
     * method which paints all the tiles/
     * @param g graphics component
     */
    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        backgroundSetUp(g);
        tileRenderer(g);
        String scoreString = "Your score is:"+ score;
        g.setColor(Color.BLACK);
        g.drawString(scoreString,410,510);
    }
    /**
     * Method where movement of the snake is realized through mouse input.
     * @param e mouse event listener.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameHasNotEnded) {
            if (isGameInPlay && isGameUnPaused) {
                int row = this.getBoardCoordinates(e.getY());
                int col = this.getBoardCoordinates(e.getX());

                if (this.selectedSnake != null) {
                    Tile tile = this.selectedSnake;
                    isSnakeMoveValid(row, col, tile);
                    return;
                }
                if (row <= 8 && col <= 16) {
                    if (this.hasBoardSnakeTile(row, col)) {
                        Tile snakeHeadTest = this.getBoardTile(row, col);
                        if (snakeHeadTest.isHead()) {
                            this.selectedSnake = this.getBoardTile(row, col);
                        } else Modal.render(this, "Warning!", "You can select only the head of the snake!");
                    } else Modal.render(this, "Warning!", "You can select only the snake which is the black circle!");
                } else Modal.render(this, "Warning!", "Out of bounds!");
            } else Modal.render(this, "Warning!", "Game is not started or is paused.To start the game click the Start button.To pause/unpause the game click the Pause button");
        } else Modal.render(this,"Warning!","Game has ended please restart!");
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
     * setter for isGameInPlay
     * @param gameInPlay new value.
     */
    public static void setGameInPlay(boolean gameInPlay) {
        isGameInPlay = gameInPlay;
    }

    /**
     * setter for isGameUnPaused
     * @param isGameUnPaused new value.
     */
    public static void setIsGameUnPaused(boolean isGameUnPaused) {
        GameBoard.isGameUnPaused = isGameUnPaused;
    }

    /**
     * Sets up background of game board.
     * @param g graphics component
     */
    private void backgroundSetUp(Graphics g){
        for(int row = 1; row<9;row++){
            for(int col = 1; col < 17; col++ ){
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
        for(int i = 0; i<16;i++) {
            int randomRow = ThreadLocalRandom.current().nextInt(1,9);
            int randomCol = ThreadLocalRandom.current().nextInt(1,17);
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
        for(int i = 0; i<21;i++) {
            int randomRow = ThreadLocalRandom.current().nextInt(1,9);
            int randomCol = ThreadLocalRandom.current().nextInt(1,17);
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
        for(int row = 0; row<10;row++){
            for(int col = 0; col < 18; col++ ){
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
     * @param snake snake object.
     */
    private void isSnakeMoveValid(int row, int col, Tile snake){
        if(snake.isMoveValid(row,col)){
            int initialRow = snake.getRow();
            int initialCol = snake.getCol();
            if(row>8){
                snakeMovement(1, col, snake, initialRow, initialCol);
            } else if(col>16){
                snakeMovement(row, 1, snake, initialRow, initialCol);
            }else if(col<1){
                snakeMovement(row, 16, snake, initialRow, initialCol);
            }else if(row<1){
                snakeMovement(8, col, snake, initialRow, initialCol);
            } else {
                snakeMovement(row, col, snake, initialRow, initialCol);
            }
            this.customArrayList.update(initialRow,initialCol,this.tileCollection);
        } else {
            Modal.render(this,"Warning!","Invalid move");
        }
    }

    /**
     * Method where snake is moved
     * @param row row being moved to
     * @param col col being moved to
     * @param snake snake itself
     * @param initialRow initial row
     * @param initialCol initial col
     */
    private void snakeMovement(int row, int col, Tile snake, int initialRow, int initialCol) {
            gameEndChecker(row,col);
            snake.move(row, col);
            snakePartInitializer(row, col, initialRow, initialCol);
            scoreCounter(row, col);
            this.tileCollection[snake.getRow()][snake.getCol()] = this.selectedSnake;
            this.tileCollection[initialRow][initialCol] = null;
            snakePartArrayRenderer(initialRow, initialCol);
            this.selectedSnake = null;
            this.repaint();
    }

    /**
     * Puts snakeUpdater into the array when it is not null and make it null again
     * @param initialRow row where the food was eaten
     * @param initialCol col where the food was eaten
     */
    private void snakePartArrayRenderer(int initialRow, int initialCol) {
        if(snakeUpdater != null){
                this.tileCollection[initialRow][initialCol] = snakeUpdater;
                snakeUpdater = null;
        }
    }

    /**
     * Initializes a snake part when a food is eaten
     * @param row being checked for food
     * @param col col being checked for food
     * @param initialRow row of snake part being spawned
     * @param initialCol col of snake part being spawned
     */
    private void snakePartInitializer(int row, int col,int initialRow,int initialCol) {
        if(this.tileCollection[row][col] != null) {
            if (this.tileCollection[row][col].getColor().equals(Color.GREEN)){
                    Tile tile = new SnakeTile(initialRow, initialCol, Color.BLACK);
                    snakeUpdater = tile;
                    customArrayList.add(tile);
                    snakeUpdater.setHead(false);
            }
        }
    }

    /**
     * Checks if the snake has entered the coordinates of food and updates the field and score.
     * @param row checked row
     * @param col checked col
     */
    private void scoreCounter(int row, int col) {
        if(this.tileCollection[row][col] != null) {
            Tile tile = getBoardTile(row, col);
            if(tile.getColor().equals(Color.GREEN)){
                score = score + 15;
                this.tileCollection[row][col] = null;
                if(score>=300){
                    Modal.render(this,"Congratulations","You win!");
                    System.exit(0);
                }
            }
        }
    }
    /**
     * Checks if the snake has entered the coordinates of an obstacle and ends the game with a modal window.
     * @param row checked row
     * @param col checked col
     */
    private void gameEndChecker(int row, int col) {
        if(this.tileCollection[row][col] != null) {
            Tile tile = getBoardTile(row, col);
            if(tile.getColor().equals(Color.RED)||tile.getColor().equals(Color.BLACK)){
                Modal.render(this,"GAME OVER","GAME OVER");
                gameHasNotEnded = false;
            }
        }
    }

    /**
     * Button set up for button panel.
     */
    private void buttonSetUp() {
        buttonPanel.add(startButton);
        startButton.addActionListener(new StartButtonListener());
        buttonPanel.add(restartButton);
        restartButton.addActionListener(this);
        buttonPanel.add(pauseButton);
        pauseButton.addActionListener(new PauseButtonListener());
        this.getContentPane().add(buttonPanel);
        setLayout(new BorderLayout());
        add(buttonPanel,BorderLayout.SOUTH);
    }

    /**
     * restart functionality for restart button
     * @param e action event listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restartButton){
            GameBoard gameBoard = new GameBoard();
            this.dispose();
        }
    }
}

/**
 * Class for start button
 */
class StartButtonListener implements ActionListener{

    /**
     * Stars game when clicked.
     * @param e action listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard.setGameInPlay(true);
    }
}
/**
 * Class for pause button
 */
class PauseButtonListener implements ActionListener{
    private int pauseCounter = 0;

    /**
     * pauses or unpauses depending if the counter is even or odd.
     * @param e action listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pauseCounter%2==0) {
            pauseCounter++;
            GameBoard.setIsGameUnPaused(false);
        } else{
            pauseCounter++;
            GameBoard.setIsGameUnPaused(true);
        }
    }
}