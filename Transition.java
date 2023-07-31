public class Transition {
    private State currentState;
    private String readSymbol;
    private State nextState;
    private String writeSymbol;
    private String stepDirection;


    // Initializes the transition function
    // Q x Γ -> Q x Γ x R
    public Transition(State currentState, String readSymbol, State nextState, String writeSymbol, String stepDirection){
        this.currentState = currentState;
        this.readSymbol = readSymbol;
        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.stepDirection = stepDirection;
    }


    public State getCurrentState() {
        return this.currentState;
    }

    public String getReadSymbol() {
        return this.readSymbol;
    }

    public State getNextState() {
        return this.nextState;
    }

    public String getWriteSymbol() {
        return this.writeSymbol;
    }

    public String getStepDirection() {
        return this.stepDirection;
    }
}
