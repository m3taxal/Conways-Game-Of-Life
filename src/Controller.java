
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Controller {

    Cell[][] cells;

    /**
     * <pre>
     * Calculate the amount of living neighbour cells each cell has.
     * For example,
     *
     * XXC
     * XCX
     * XCC
     *
     * here the middle cell (C - living cell, X - dead cell) has 3 living neighbour cells.
     * </pre>
     *
     * @return  amount of living neighbour cells
     */
    private int[][] checkNeighbouringCells(){
        int[][] neighbours = new int[99][99];

        for(int column = 1; column < 99; column++){
            for(int row = 1; row < 99; row++){
                int livingNeighbours = 0; //number of living neighbour cells


                //all 8 positions of neighbour cells
                if(cells[column][row-1].isCellAlive()){ //TOP MIDDLE
                    livingNeighbours += 1;
                }

                if(cells[column][row+1].isCellAlive()){ //BOTTOM MIDDLE
                    livingNeighbours += 1;
                }

                if(cells[column-1][row-1].isCellAlive()){ //TOP LEFT
                    livingNeighbours += 1;
                }

                if(cells[column+1][row-1].isCellAlive()){ //TOP RIGHT
                    livingNeighbours += 1;
                }

                if(cells[column-1][row].isCellAlive()){ //MIDDLE LEFT
                    livingNeighbours += 1;
                }

                if(cells[column+1][row].isCellAlive()){ //MIDDLE RIGHT
                    livingNeighbours += 1;
                }

                if(cells[column-1][row+1].isCellAlive()){ //BOTTOM LEFT
                    livingNeighbours += 1;
                }


                if(cells[column+1][row+1].isCellAlive()){ //BOTTOM RIGHT
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
        int[][] neighbours = checkNeighbouringCells();

        for(int i = 0; i < 99; i++){
            for(int j = 0; j < 99; j++){
                //implement all the Conway's Game Of Life rules

                if(cells[i][j].isCellAlive()){ //For a space that is populated:

                    //Each cell with one or no neighbor's dies, as if by solitude.
                    if(neighbours[i][j] < 2){
                        cells[i][j].killCell();
                    }

                    //Each cell with two or three neighbors survives.
                    if(neighbours[i][j] == 2 || neighbours[i][j] == 3){
                        cells[i][j].reviveCell();
                    }

                    //Each cell with four or more neighbors dies, as if by overpopulation.
                    if(neighbours[i][j] > 3){
                        cells[i][j].killCell();
                    }
                }

                //For a space that is empty or unpopulated:
                //Each cell with three neighbors becomes populated.
                if(!cells[i][j].isCellAlive() && neighbours[i][j] == 3){
                    cells[i][j].reviveCell();
                }
            }
        }
    }

    public Controller(GridPane gridPane){
        this.cells = new Cell[100][100];

        for(int column = 0; column < 100; column++){
            for(int row = 0; row < 100; row++){
                //leave game edges as dead cells, so they function as borders
                if(column == 0 || column == 99 || row == 0 || row == 99){
                    cells[column][row] = new Cell(new Rectangle(8, 8), Cell.cellState.DEAD, 0);
                }
                else {
                    cells[column][row] = new Cell(new Rectangle(8, 8), Cell.cellState.DEAD, 10); //currently, 10% chance of every cell being alive at the start
                }

                //give player the ability to revive cells by clicking
                int finalRow = row;
                int finalColumn = column;
                cells[column][row].getCellBody().setOnMouseClicked(mouseEvent -> {
                    if(cells[finalColumn][finalRow].isCellAlive()){
                        cells[finalColumn][finalRow].killCell();
                    }
                    else {
                        cells[finalColumn][finalRow].reviveCell();
                    }
                });

                gridPane.add(cells[column][row].getCellBody(), column, row);
            }
        }
    }
}
