
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class runGame extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    private GridPane grid;

    /**
     * Initialise the game grid for Conway's Game Of Life
     */
    private void initGrid(){
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        final int numCols = 100 ;
        final int numRows = 100 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            grid.getRowConstraints().add(rowConst);
        }
    }

    @Override
    public void start(Stage stage) {
        initGrid();

        Scene scene = new Scene(grid, 800, 800);

        Controller controller = new Controller(grid);

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                controller.evolve();
            }
        });

        stage.setTitle("Conway's Game Of Life");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }
}
