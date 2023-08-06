// [STALGCM] Multitape Turing Machine
// Bantolino, Jana Marie S.
// Dimalanta, Jason Erwin Clyde V.

// Things needed for the machine:
// 1. Multiple tapes
// 2. States
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
public class Mttm {
    public static void main(String[] args) {
        try {
            File machineFile = new File("innit.txt"); //set file name to be read
            Scanner fileReader = new Scanner(machineFile); //create a reader object to read the input file
            int numStates = fileReader.nextInt(); //First line in input file should always be the number of states 

            ArrayList<State> stateArray = new ArrayList<>(); // ArrayList to store the states to be generates
            //initialize the states and statenames
            for(int i = 0; i < numStates; i++){
                //TODO: Add input validation to ensure uniqueness of state names e.g: no repeated state names be added in list.
                State stateObj = new State(fileReader.next()); //read next entry in line
                stateArray.add(stateObj); //add object to array
                // System.out.println("State name of index = " + i + " is: " + stateArray.get(i).getName());
            }

            //read initial state
            String readInitial = fileReader.next();
            String initialState = "";
            for (State state : stateArray) {
                if(state.getName().equals(readInitial)){
                    initialState = initialState.concat(readInitial);
                }
            }
            // System.out.println("Initial state = "+ initialState);

            //read num final states
            int numFinalStates = fileReader.nextInt();
            ArrayList<State> finalStates = new ArrayList<>();
            for (int i = 0; i < numFinalStates; i++) {
                String read = fileReader.next();
                for (State state : stateArray) {
                    if(state.getName().equals(read)){
                        // System.out.println("Final state #" + i +" is = " + state.getName());
                        finalStates.add(state);
                    }
                }
            }

            //Read the set of input alphabet
            int numTapeInputs = fileReader.nextInt();
            ArrayList<String> inputAlphabet = new ArrayList<>();

            for(int i = 0; i < numTapeInputs; i++){
                //TODO: Add input validation to ensure uniqueness of input alphabet e.g: no repeated output characters be added in list.
                inputAlphabet.add(fileReader.next()); //add object to array
                // System.out.println("State input of index = " + i + " is: " + inputAlphabet.get(i));
            }
            
            //Read the set of output alphabet
            int numTapeOutputs = fileReader.nextInt();
            ArrayList<String> outputAlphabet = new ArrayList<>();

            for (int i = 0; i < numTapeOutputs; i++) {
                //TODO: Add input validation to ensure uniqueness of output alphabet e.g: no repeated output characters be added in list.
                outputAlphabet.add(fileReader.next());
                // System.out.println("Output index: " + i + " string = " + outputAlphabet.get(i));
            }

            //Read the number of tapes to be used in the Multi Tape Turing Machine 
            int numTapes = fileReader.nextInt();
            fileReader.nextLine();
            // System.out.println("Number of tapes of the machine is = " + numTapes);

            //Read the set of transition functions
            int numTransitions = fileReader.nextInt();
            fileReader.nextLine();
            int counter = 0; // transition counter 
            int tapeCounter = 0; //used to set which tape does the transition belong to
            Transition[][] transitionArray = new Transition[numTapes][numTransitions];
            while (counter < numTransitions) {
                //read transition lines
                String line = fileReader.nextLine();
                // System.out.println(line);

                //If line is an X, increment counter by 1 this signifies the end of the transition
                //succeeding entries belong to the next should they exist
                if(line.equals("X")){
                    counter++;
                    // System.out.println(counter);
                    tapeCounter = 0;
                    // System.out.println("tape counter in if = " + tapeCounter);
                }else{
                    //Split line input into tokens using space as a delimiter
                    String[] tokens = line.split(" ");
                    
                    //get the index of the states which equal the names in the transition function
                    int currentStateIndex = findIndex(tokens[0], stateArray); 
                    int nextStateIndex = findIndex(tokens[4], stateArray);

                    //pass them into the transition array
                    // System.out.println("tape counter in else = " + tapeCounter);
                    transitionArray[tapeCounter][counter] = new Transition(stateArray.get(currentStateIndex), tokens[1], stateArray.get(nextStateIndex), tokens[2], tokens[3]);
                    // System.out.println("Current state = " + transitionArray[tapeCounter][counter].getCurrentState().getName());
                    tapeCounter++;
                }
            }

            //DONE: set is initial state
            /*
             * get the initial state of the machine and set it to the state
             */
            int iniitialStateIndex = findIndex(initialState, stateArray); 
            stateArray.get(iniitialStateIndex).setInitial(true);
            // System.out.println(stateArray.get(iniitialStateIndex).getName()+stateArray.get(iniitialStateIndex).isInitial());
            
            //DONE: set is final state
            /*
             * For each final state in the set of final states find the index within the array list
             * Uppon finding the index, get the associated state object and set it as a final state.
             */
            for (State finalState : finalStates) {
                int finalStateIndex = findIndex(finalState.getName(), finalStates);
                finalStates.get(finalStateIndex).setFinal(true);
                // System.out.println("Final State = " + finalStates.get(finalStateIndex).getName() + finalStates.get(finalStateIndex).isFinal());
            }
            //DONE: pass transitionArray into state class
            //get the number of transitions per state respective to their tapes
            for (int i = 0; i < numTapes; i++) {//loop through each tape
                for (int j = 0; j < numTransitions; j++) { //loop through each transition per tape
                    String stateName = transitionArray[i][j].getCurrentState().getName();
                    int stateIndex = findIndex(stateName, stateArray);
                    stateArray.get(stateIndex).addTransition(i, transitionArray[i][j]);
                }
            }

            // for (State state : stateArray) {
            //     System.out.println("transition of state " + state.getName() + " is = " + state.getTransitionMap());
            // }
            
            //Create set of tapes and initalize their input
            Tape[] tapeArray = new Tape[numTapes];
            for (int i = 0; i < numTapes; i++) {
                tapeArray[i] = new Tape(stateArray, i);
                if(fileReader.hasNextLine()){
                    String tapeInput = fileReader.nextLine();
                    tapeArray[i].initializeTape(tapeInput);
                }else{
                    tapeArray[i].initializeTape();
                }
                // System.out.println(tapeArray[i].getTapeContent() + " " + tapeArray[i].getStates());
            }
            fileReader.close();

            int counterist = 0;
            System.out.println("ebefore whiel");
            while(tapesNotFinished(tapeArray)){
                for (int i = 0; i < tapeArray.length; i++) {
                    tapeArray[i].runTape();
                }      
            }
            System.out.println("out of while");

        } catch (FileNotFoundException e) {
            // TODO: handle exception
            System.out.println("File does not exist");
        }
    }

    private static int findIndex(String stateName, ArrayList<State> list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(stateName)){
                return i;
            }
        }
        return -1;
    }
    private static boolean tapesNotFinished(Tape[] tapes){
        int numTapes = tapes.length;
        int finishedCounter = 0;

        for (Tape tape : tapes) {
            if(tape.isFinished()){
                finishedCounter++;
            }
        }
        if(finishedCounter != numTapes){
            return true;
        }else{
            return false;
        }
    }
}