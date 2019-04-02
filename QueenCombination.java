import java.util.ArrayList;
import java.util.Collections;

public class QueenCombination {

    // Parameters
    private ArrayList<Square> queens;
    private double energy;
    private boolean solution;

    public QueenCombination(){
        this.queens = new ArrayList<>();
        solution = false;
        energy = Constants.PROBLEM_SIZE;
    }
    public QueenCombination(int size){
        this.queens = new ArrayList<>();
        solution = false;
        for(int index=1; index<=size; index++) {
            this.queens.add(new Square((char)(Math.random()*Constants.BOARD_SIZE_Y+'A'), index));
        }
        measureEnergy();
        if (energy==0)
            solution=true;
    }

    // Getters
    public ArrayList<Square> getQueens() {
        return queens;
    }
    public double getEnergy() {
        return energy; }
    public boolean isSolution() {
        return solution;
    }
    public ArrayList<QueenCombination> generateNeighborStates(int queenIndex){
        ArrayList<QueenCombination> neighborStates = new ArrayList<>();

        char avoidIndex = queens.get(queenIndex).getCoordX();

        for(char x='A'; x<'A'+Constants.BOARD_SIZE_X; x++){
            QueenCombination tempQueensCombination1 = new QueenCombination();
            tempQueensCombination1.copy(this);
            tempQueensCombination1.getQueens().get(queenIndex).setCoordX(x);
            if(tempQueensCombination1.getQueens().get(queenIndex).isValid() && x!=avoidIndex){
                tempQueensCombination1.measureEnergy();
                if (tempQueensCombination1.getEnergy()==0)
                    tempQueensCombination1.setSolution(true);
                neighborStates.add(tempQueensCombination1);
            }
        }

        Collections.shuffle(neighborStates);
        return neighborStates;
    }

    // Setters
    public void measureEnergy() {
        this.energy = SimulatedAnnealing.measureEnergy(this);
    }
    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    // To String
    public String toString(){
        String string = "";
        for(Square s: queens)
            string+= s.toString();
        return string+" enery="+energy+"J";
    }
    public String toString(String title){
        return title +": "+ toString();
    }

    // Methods
    public void copy(QueenCombination source){
        for(Square s: source.getQueens())
            queens.add(new Square(s.getCoordX(), s.getCoordY()));
    }
}