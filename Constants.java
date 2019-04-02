public interface Constants {

    // Problem size
    int PROBLEM_SIZE = 9;
    int BOARD_SIZE = PROBLEM_SIZE;
    int BOARD_SIZE_X = BOARD_SIZE;
    int BOARD_SIZE_Y = BOARD_SIZE;

    // Simulated Annealing Algorithm
    double INITIAL_PROBABILITY = 0.90;
    double DELTA = PROBLEM_SIZE/4;
    double INITIAL_TEMPERATURE = -DELTA/Math.log(INITIAL_PROBABILITY); // 1.44269 K
    double TEMPERATURE_SCHEDULE_MULTIPLIER = 0.98;   // Controls the cool-down
    double FINAL_TEMPERATURE = INITIAL_TEMPERATURE*0.001;    // Hill Climbing Switch

    // JavaFX
    double BOARD_HEIGHT = 500; // Pixels
    double BOARD_WIDTH = BOARD_HEIGHT;  // Pixels
    double SQUARE_SIZE = BOARD_HEIGHT/BOARD_SIZE;    // Pixels
    double WIDTH = BOARD_WIDTH*1.7;    // Pixels
    double HEIGHT = BOARD_HEIGHT+SQUARE_SIZE;   // Pixels

    // parameters
    int STEPS = 10000;
    boolean SKIP_STEPS = STEPS>1;
    boolean DEBUG = true;
    boolean SEARCH_NEIGHBOR_STATES = true;

}
