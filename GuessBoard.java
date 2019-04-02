import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GuessBoard extends Pane {

    // Parameters
    private QueenCombination currentQueensCombination, candidateNext, nextQueenCombination;
    private Temperature T;
    private boolean solution;
    private long visitedStatesCount;

    // Constructor
    public GuessBoard(){
        currentQueensCombination = new QueenCombination(Constants.PROBLEM_SIZE);
        nextQueenCombination = new QueenCombination();
        solution = false;
        visitedStatesCount=1;
        T = new Temperature();
        drawCurrentState();
    }

    // Getters
    public void next(){
        for(int index=0; index<Constants.PROBLEM_SIZE; index++) {
            visitedStatesCount++;
            if (!currentQueensCombination.isSolution()) {
                if (T.getTemperature() <= Constants.FINAL_TEMPERATURE)
                    restart();
                move();
                getCandidateNext(index);
                if (Constants.DEBUG) {
                    System.out.println(T.toString());
                    System.out.println(currentQueensCombination.toString("Current combination"));
                }
            } else
                solution = true;
        }
        T.decreaseTemperature();
    }
    public boolean isQuenching(){
        return T.getTemperature() <= Constants.FINAL_TEMPERATURE;
    }
    private void getCandidateNext(int queenIndex){
        if (Constants.SEARCH_NEIGHBOR_STATES)
            candidateNext = currentQueensCombination.generateNeighborStates(queenIndex).get(0);
        else candidateNext = new QueenCombination(Constants.PROBLEM_SIZE);
        if (SimulatedAnnealing.accept(currentQueensCombination, candidateNext, T)) {
            nextQueenCombination = candidateNext;
        }
    }
    private void move(){
        if(!nextQueenCombination.getQueens().isEmpty())
            currentQueensCombination = nextQueenCombination;
    }
    public void restart(){
        T.resetTemperature();
        currentQueensCombination = new QueenCombination(Constants.PROBLEM_SIZE);
        candidateNext = null;
        nextQueenCombination = new QueenCombination();
    }
    public boolean hasSolution(){
        return solution;
    }
    public long getVisitedStatesCount() {
        return visitedStatesCount;
    }

    // Draw
    public void draw(){
        drawCurrentState();
        if(solution==false)
            drawCandidateNext();
    }
    private void drawCurrentState(){
        getChildren().clear();
        ArrayList<ImageView> queens = new ArrayList<>();
        for(Square s: currentQueensCombination.getQueens()){
            ImageView queen = null;
            try{
                Image whiteQueenImage;
                whiteQueenImage = new Image(new FileInputStream("src/images/wQueen.png"));
                queen = new ImageView(whiteQueenImage);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            queen.setX(Constants.SQUARE_SIZE*(s.getStandardCoordX()+0.05));
            queen.setY(Constants.SQUARE_SIZE*(s.getStandardCoordY()+0.05));
            queen.setFitWidth(Constants.SQUARE_SIZE*0.9);
            queen.setPreserveRatio(true);
            queen.setSmooth(true);
            queen.setCache(true);
            queens.add(queen);
        }
        Text temperature = new Text(Constants.BOARD_WIDTH+Constants.SQUARE_SIZE,Constants.SQUARE_SIZE, T.toString());
        double r = (T.getTemperature()-Constants.FINAL_TEMPERATURE)/(Constants.INITIAL_TEMPERATURE-Constants.FINAL_TEMPERATURE);
        temperature.setFill(Color.rgb((int)(255*r), (int)(255*(1-r)), (int)(255*(1-r))));
        Text energy = new Text(Constants.BOARD_WIDTH+Constants.SQUARE_SIZE,Constants.SQUARE_SIZE+20, "Current state:\nEnergy="+currentQueensCombination.getEnergy()+"J");

        getChildren().addAll(queens);
        getChildren().addAll(energy, temperature);
    }
    private void drawCandidateNext(){
        ArrayList<Rectangle> squares = new ArrayList<>();
        for(Square s: candidateNext.getQueens()){
            Rectangle rectangle = new Rectangle(s.getStandardCoordX()*Constants.SQUARE_SIZE, s.getStandardCoordY()*Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(3);
            squares.add(rectangle);
        }
        Text energy = new Text(Constants.BOARD_WIDTH+Constants.SQUARE_SIZE,Constants.SQUARE_SIZE+60, "Candidate next:\nEnergy="+candidateNext.getEnergy()+"J");
        energy.setFill(Color.RED);

        getChildren().addAll(squares);
        getChildren().addAll(energy);

        if(candidateNext.getEnergy()>currentQueensCombination.getEnergy()) {
            Text acceptanceProbability = new Text(Constants.BOARD_WIDTH + Constants.SQUARE_SIZE, Constants.SQUARE_SIZE + 100, "Acceptance Probability=" + (new DecimalFormat("0.00%").format(SimulatedAnnealing.getAcceptanceProbability(currentQueensCombination, candidateNext, T))));
            acceptanceProbability.setFill(Color.BLUE);
            getChildren().add(acceptanceProbability);
        }

    }
}
