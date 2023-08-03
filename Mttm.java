// [STALGCM] Multitape Turing Machine
// Bantolino, Jana Marie S.
// Dimalanta, Jason Erwin Clyde V.

// Things needed for the machine:
// 1. Multiple tapes
// 2. States
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
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
                System.out.println("State name of index = " + i + " is: " + stateArray.get(i).getName());
            }

            //read initial state
            String readInitial = fileReader.next();
            String initialState = "";
            for (State state : stateArray) {
                if(state.getName().equals(readInitial)){
                    initialState = initialState.concat(readInitial);
                }
            }
            System.out.println("Initial state = "+ initialState);

            //read num final states
            int numFinalStates = fileReader.nextInt();
            ArrayList<State> finalStates = new ArrayList<>();
            for (int i = 0; i < numFinalStates; i++) {
                String read = fileReader.next();
                for (State state : stateArray) {
                    if(state.getName().equals(read)){
                        System.out.println("Final state #" + i +" is = " + state.getName());
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
                System.out.println("State input of index = " + i + " is: " + inputAlphabet.get(i));
            }
            
            //Read the set of output alphabet
            int numTapeOutputs = fileReader.nextInt();
            ArrayList<String> outputAlphabet = new ArrayList<>();

            for (int i = 0; i < numTapeOutputs; i++) {
                //TODO: Add input validation to ensure uniqueness of output alphabet e.g: no repeated output characters be added in list.
                outputAlphabet.add(fileReader.next());
                System.out.println("Output index: " + i + " string = " + outputAlphabet.get(i));
            }

            //Read the number of tapes to be used in the Multi Tape Turing Machine 
            int numTapes = fileReader.nextInt();
            fileReader.nextLine();
            System.out.println("Number of tapes of the machine is = " + numTapes);

            //Read the set of transition functions
            int numTransitions = fileReader.nextInt();
            fileReader.nextLine();
            int counter = 0; // transition counter 
            int tapeCounter = 0; //used to set which tape does the transition belong to
            Transition[][] transitionArray = new Transition[numTapes][numTransitions];
            while (counter < numTransitions) {
                //read transition lines
                String line = fileReader.nextLine();
                System.out.println(line);

                //If line is an X, increment counter by 1 this signifies the end of the transition
                //succeeding entries belong to the next should they exist
                //TODO: flush out logic of transition parsing
                if(line.equals("X")){
                    counter++;
                    System.out.println(counter);
                    tapeCounter = 0;
                    System.out.println("tape counter = " + tapeCounter);
                }else{
                    //Split line input into tokens using space as a delimiter
                    String[] tokens = line.split(" ");
                    
                    //get the index of the states which equal the names in the transition function
                    int currentStateIndex = findIndex(tokens[0], stateArray); 
                    int nextStateIndex = findIndex(tokens[4], stateArray);

                    //pass them into the transition array
                    transitionArray[tapeCounter][counter] = new Transition(stateArray.get(currentStateIndex), tokens[1], stateArray.get(nextStateIndex), tokens[2], tokens[3]);
                    System.out.println(transitionArray[tapeCounter][counter]);
                    tapeCounter++;
                    System.out.println("tape counter = " + tapeCounter);
                }

            }



            fileReader.close();
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

}