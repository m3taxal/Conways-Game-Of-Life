import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    private final Rectangle cellBody;

    enum cellState {DEAD, ALIVE}

    private cellState state;

    public Rectangle getCellBody() {
        return cellBody;
    }

    public Cell(Rectangle cellBody, cellState state, int aliveProb) {
        this.cellBody = cellBody;
        this.state = state;

        //each cell has probability of being alive at the start of the game (aliveProp determines this probability)
        if(aliveProb < 0 || aliveProb > 100)
            throw new IllegalArgumentException("Probability of cell being alive cannot be under 0% or above 100%!");

        int ranCellNum = getRandomNumber(1, 100);
        if(ranCellNum <= aliveProb){
            reviveCell();
        }
        else {
            killCell();
        }
    }

    public void killCell(){
        this.cellBody.setFill(Color.TRANSPARENT);
        this.state = cellState.DEAD;
    }

    public void reviveCell(){
        this.cellBody.setFill(Color.RED);
        this.state = cellState.ALIVE;
    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max-min))+min);
    }

    public boolean isCellAlive(){
        return this.state == cellState.ALIVE;
    }
}






