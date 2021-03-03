package CustomArrayList;

import tiles.Tile;

public class CustomArrayList{

    private int placeholderPointer=0;
    private Tile[] collection;
    private int initialRow;
    private int initialCol;

    /**
     * Constructor that initializes the array
     */
    public CustomArrayList(){
        this.collection =  new Tile[100];
    }

    /**
     * Method which adds a string to the array
     * @param tile Added tile.
     */
    public void add(Tile tile){
        this.collection[placeholderPointer++] = tile;
    }


    /**
     * Method which updates the snake body and makes the body parts follow the head.
     */
    public void update(int headInitialRow,int headInitialCol,Tile[][] tileCollection) {
        if (collection[0] != null) {
            int counter = 0;
            for (Tile i : collection) {
                if(i!= null) {
                    initialRow = i.getRow();
                    initialCol = i.getCol();
                    if (counter == 0) {
                        tileCollection[initialRow][initialCol] = null;
                        i.move(headInitialRow, headInitialCol);
                        tileCollection[i.getRow()][i.getCol()] = i;
                        counter++;
                    } else {
                        tileCollection[initialRow][initialCol] = null;
                        i.move(initialRow, initialCol);
                        tileCollection[i.getRow()][i.getCol()] = i;
                    }
                }
            }
        }
    }
}
