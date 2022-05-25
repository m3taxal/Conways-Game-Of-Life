
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {
    private final Rectangle[][] cellBodies;

    /**
     * Finds out number of living neighbour cells
     *
     * @param cellBodies    all cells in "grid-like" form (e.g. [column][row])
     * @return              neighbour cells in "grid-like" form
     */
    private int[][] checkNeighbouringCells(Rectangle[][] cellBodies){
        int[][] neighbours = new int[99][99];

        for(int column = 1; column < 99; column++){ //column
            for(int row = 1; row < 99; row++){ //row
                int livingNeighbours = 0; //number of living neighbours


                //all 8 positions of neighbour cells
                if(isCellAlive(cellBodies[column][row-1])){ //TOP MIDDLE
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column][row+1])){ //BOTTOM MIDDLE
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column-1][row-1])){ //TOP LEFT
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column+1][row-1])){ //TOP RIGHT
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column-1][row])){ //MIDDLE LEFT
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column+1][row])){ //MIDDLE RIGHT
                    livingNeighbours += 1;
                }

                if(isCellAlive(cellBodies[column-1][row+1])){ //BOTTOM LEFT
                    livingNeighbours += 1;
                }


                if(isCellAlive(cellBodies[column+1][row+1])){ //BOTTOM RIGHT
                    livingNeighbours += 1;
                }

                neighbours[column][row] = livingNeighbours;
            }
        }

        return neighbours;
    }

    /**
     * "Evolve" current generation of cells to next generation.
     * Edges of the game grid will stay as dead cells to function as borders.
     */
    public void evolve(){
        int[][] neighbours = checkNeighbouringCells(cellBodies);

        for(int i = 0; i < 99; i++){
            for(int j = 0; j < 99; j++){
                //implement all the Conway's Game Of Life rules

                if(cellBodies[i][j].getFill().equals(Color.RED)){ //For a space that is populated:

                    //Each cell with one or no neighbor's dies, as if by solitude.
                    if(neighbours[i][j] < 2){
                        cellBodies[i][j].setFill(Color.TRANSPARENT);
                    }

                    //Each cell with two or three neighbors survives.
                    if(neighbours[i][j] == 2 || neighbours[i][j] == 3){
                        cellBodies[i][j].setFill(Color.RED);
                    }

                    //Each cell with four or more neighbors dies, as if by overpopulation.
                    if(neighbours[i][j] > 3){
                        cellBodies[i][j].setFill(Color.TRANSPARENT);
                    }
                }

                //For a space that is empty or unpopulated:
                //Each cell with three neighbors becomes populated.
                if(cellBodies[i][j].getFill().equals(Color.TRANSPARENT) && neighbours[i][j] == 3){
                    cellBodies[i][j].setFill(Color.RED);
                }
            }
        }
    }

    /**
     * Generate a random number in a specified range
     * @param min   minimum of the random number
     * @param max   maximum of the random number
     * @return      the random number as an Integer
     */
    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max-min))+min);
    }

    /**
     * Revive cell
     *
     * @param cell  Cell to be revived
     */
    private void reviveCell(Rectangle cell){
        cell.setFill(Color.RED);
    }

    /**
     * Kill cell
     *
     * @param cell cell that is to be killed
     */
    private void killCell(Rectangle cell){
        cell.setFill(Color.TRANSPARENT);
    }

    /**
     * Check if cell is dead
     *
     * @param cell  cell to be checked
     * @return      true if cell is dead, false if cell is alive
     */
    private boolean isCellDead(Rectangle cell){
        return cell.getFill().equals(Color.TRANSPARENT);
    }

    /**
     * Check if cell is alive
     *
     * @param cell  cell to be checked
     * @return      true if cell is alive, false if cell is dead
     */
    private boolean isCellAlive(Rectangle cell){
        return cell.getFill().equals(Color.RED);
    }

    /**
     * Initialise cells inside game grid
     *
     * @param gridPane  the game grid
     */
    public Controller(GridPane gridPane){
        cellBodies = new Rectangle[100][100]; //column, row

        for(int currentColumn = 0; currentColumn < cellBodies.length; currentColumn++){
            for(int currentRow = 0; currentRow < 100; currentRow++){
                cellBodies[currentColumn][currentRow] = new Rectangle(); //add rectangle to every cell of the grid to control bg-color

                Rectangle temp = cellBodies[currentColumn][currentRow];
                temp.setHeight(8);
                temp.setWidth(8);
                killCell(temp); //set all cells to "DEAD" (TRANSPARENT == "DEAD" cell)

                //give player ability to kill and revive cells inside the game
                cellBodies[currentColumn][currentRow].setOnMouseClicked(mouseEvent -> {
                    if(isCellDead(temp)){
                        reviveCell(temp);
                    }
                    else {
                        killCell(temp);
                    }
                });

                gridPane.add(temp, currentColumn, currentRow);
            }
        }

        //reviveRandomCells(20);
    }

    /**
     * Generate a random amount of living cells
     *
     * @param chance    chance of a single cell becoming alive
     */
    private void reviveRandomCells(int chance){
        //every cell has a random chance to be alive at initialisation of the game (leave out edges of game grid)
        for(int i = 1; i < 99; i++){
            for(int j = 1; j < 99; j++){
                int ranInt = getRandomNumber(1, 100);

                if(ranInt <= chance){ //currently, 20% chance for every cell to be alive at the start
                    cellBodies[i][j].setFill(Color.RED);
                }
                else {
                    cellBodies[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }
    }
}
