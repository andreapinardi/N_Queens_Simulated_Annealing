import java.util.ArrayList;

public class SimulatedAnnealing {

    public static double measureEnergy(QueenCombination state){
        int energy=0;
        int vertical=0, horizontal=0, backDiagonal=0, forwardDiagonal=0;
        ArrayList<Integer> visited = new ArrayList<>();
        for(Square s: state.getQueens()){
            if(!visited.contains(s.getIndex())) {
                visited.add(s.getIndex());
                for(Square t: state.getQueens()) {
                    if(!s.equals(t) && !visited.contains(t.getIndex())){
                        if(s.isOnSameVertical(t))
                            vertical++;
                        if(s.isOnSameHorizontal(t))
                            horizontal++;
                        if(s.isOnSameForwardDiagonal(t))
                            forwardDiagonal++;
                        if(s.isOnSameBackDiagonal(t))
                            backDiagonal++;
                    }
                }
            }
        }
        energy=vertical+horizontal+backDiagonal+forwardDiagonal;
        return energy;
    }
    public static boolean accept(QueenCombination currentQueenCombination, QueenCombination nextQueenCombination, Temperature temperature){
        if(nextQueenCombination.getEnergy()<=currentQueenCombination.getEnergy())
            return true;
        else {
            return Math.random()<=getAcceptanceProbability(currentQueenCombination, nextQueenCombination, temperature);
        }
    }

    public static double getAcceptanceProbability(QueenCombination currentQueenCombination, QueenCombination nextQueenCombination, Temperature temperature){
        return Math.exp(-(nextQueenCombination.getEnergy() - currentQueenCombination.getEnergy()) / temperature.getTemperature());
    }

}
