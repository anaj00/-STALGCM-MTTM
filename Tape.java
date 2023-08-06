import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tape {
    private ArrayList<String> left;
    private ArrayList<String> right;
    private boolean isRejected = false;
    private boolean isAccepted = false;
    private ArrayList<State> states;
    private State currentState;
    private String writeSymbol;
    private State nextState;
    private int tapeNumber;


    public Tape() {
        left = new ArrayList<>();
        right = new ArrayList<>();
    }

    public Tape(List<State> inputArray, int number) {
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.tapeNumber = number;
        this.states = (ArrayList<State>) inputArray;
        for (State state : inputArray) {
            if(state.isInitial()){
                this.currentState = state;
            }
        }
        
    }

    // Returns the right side of the tape
    public ArrayList<String> getRight() {
        System.out.println(right);
        return right;
    }

    // Returns the left side of the tape
    public ArrayList<String> getLeft() {
        ArrayList<String> reversedLeft = new ArrayList<>(left);
        Collections.reverse(reversedLeft);
        System.out.print(reversedLeft);
        return reversedLeft;
    }

    // Initializes the tape for the start
    public void initializeTape() {
        for (int i = 0; i < 15; i++) {
            right.add("_");
        }
        right.add("_");
        left.add("_");
    }

    // Initializes the tape with the input
    public void initializeTape(String content) {
        char[] contentArr = content.toCharArray();

        for (int i = 0; i < contentArr.length; i++) {
            right.add(String.valueOf(contentArr[i]));
        }

        right.add("_");
        left.add("_");
    }

    // Returns the content of the tape through a string
    public String getTapeContent() {
        String leftString = "";
        String rightString = "";

        for (int i = 0; i < left.size(); i++) {
            leftString += left.get(i);
        }

        for (int i = 0; i < right.size(); i++) {
            rightString += right.get(i);
        }

        StringBuffer rLeftString = new StringBuffer(leftString).reverse();
        return rLeftString + rightString;
    }

    // Prints the content of the tape
    public void printTapeContent() {
        getLeft();
        getRight();
    }

    // Changes where the read/write head is supposed to be pointing
    public void step(String symbol) {
        if (symbol.equals("L")) {
            String temp = left.get(0);
            right.add(0, temp);
            left.remove(0);
            left.add("_");
        } 
        if (symbol.equals("R")) {
            String temp = right.get(0);
            left.add(0, temp);
            right.remove(0);
            right.add("_");
        }
    }

    // Reads the symbol of the head: right array, index 0
    public String readSymbol() {
        return right.get(0);
    }


    // Writes the symbol where the head
    public void writeSymbol(String symbol) {
        right.set(0, symbol);
    }

    public boolean isFinished(){
        if (this.isRejected) {
            return true;
        } else {
            return this.isAccepted;
        }
    }

    public boolean isAccepted(){
        return this.currentState.isFinal();
    }

    public ArrayList<State> getStates() {
        return states;
    }    


    public State getNextState(){
        return this.nextState;
    }

    public String getWrite(){
        return this.writeSymbol;
    }

    public void runTape(){
        applyTransition();
    }
    public int getNumber(){
        return this.tapeNumber;
    }

    public State getCurrentState(){
        return this.currentState;
    }

    private void applyTransition(){
        String read = readSymbol();
        System.out.println(read);
        ArrayList<Transition> list = currentState.getTransitionList();
        for (Transition transition : list) {
            if(read.equals(transition.getReadSymbol()[tapeNumber])){
                this.writeSymbol = transition.getWriteSymbol()[tapeNumber];
                writeSymbol(writeSymbol);
                step(transition.getStepDirection()[tapeNumber]);
                this.nextState =  transition.getNextState();
                this.currentState = transition.getNextState();
            }else{
                this.isRejected = true;
            }
        }
    }

    public static void main(String[] args) {
        Tape tape1 = new Tape();

        tape1.initializeTape("WOWZER");

        tape1.printTapeContent();
        tape1.step("L");
        tape1.printTapeContent();
        tape1.writeSymbol("O");
        tape1.step("L");
        tape1.writeSymbol("W");
        tape1.step("L");
        tape1.writeSymbol("Z");
        tape1.step("L");
        tape1.writeSymbol("E");
        tape1.step("L");
        tape1.writeSymbol("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");
        tape1.step("R");

        tape1.printTapeContent();
    }

}
