import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestNQueens_SA extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Objects
        StackPane stackPane;
        BorderPane borderPane;
        Chessboard chessboard;
        GuessBoard guessBoard;

        stackPane = new StackPane();
        borderPane = new BorderPane();
        chessboard = new Chessboard(Constants.BOARD_SIZE);
        guessBoard = new GuessBoard();

        stackPane.getChildren().addAll(chessboard, guessBoard);
        borderPane.setCenter(stackPane);
        borderPane.setPadding(new Insets(10));

        borderPane.setOnMouseClicked(event -> {
            if(Constants.SKIP_STEPS && !guessBoard.hasSolution()) {
                long step = 0;
                while (!guessBoard.hasSolution() /*&& !guessBoard.isQuenching() */&& step++<Constants.STEPS)
                        guessBoard.next();
                if(Constants.DEBUG)
                    System.out.println("States Explored: "+guessBoard.getVisitedStatesCount());
            }
            else
                guessBoard.next();
            guessBoard.draw();
            if(guessBoard.isQuenching()) guessBoard.restart();
        });


        // Put the pane on the scene and the scene on the stage
        Scene scene = new Scene(borderPane, Constants.WIDTH, Constants.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.PROBLEM_SIZE+" Queens with Simulated Annealing - Andrea Pinardi");
        primaryStage.show();
    }
}
