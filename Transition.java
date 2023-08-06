public class Transition {
    private State currentState;
    private String[] readSymbol;
    private State nextState;
    private String[] writeSymbol;
    private String[] stepDirection;


    // Initializes the transition function
    // Q x Γ -> Q x Γ x R
    public Transition(State currentState, String[] readSymbols, State nextState, String[] writeSymbols, String[] stepDirections){
        this.currentState = currentState;
        this.readSymbol = readSymbols;
        this.nextState = nextState;
        this.writeSymbol = writeSymbols;
        this.stepDirection = stepDirections;
    }


    public State getCurrentState() {
        return this.currentState;
    }

    public String[] getReadSymbol() {
        return this.readSymbol;
    }

    public State getNextState() {
        return this.nextState;
    }

    public String[] getWriteSymbol() {
        return this.writeSymbol;
    }

    public String[] getStepDirection() {
        return this.stepDirection;
    }
}
