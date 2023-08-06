import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TuringGui extends JFrame {
    private JButton runButton;
    private JTextArea  outArea;
    public TuringGui(){
        setTitle("Case Study: BantolinoDimalanta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720);
        initComponents();
        initLayout();
    }

    private void initComponents(){
        runButton = new JButton("Run me over");
        
      
        outArea = new JTextArea(20, 20); 
           
        outArea.setEditable(false);

        Font newFont = new Font("Arial", Font.PLAIN, 19); // Set the desired font name and size
        outArea.setFont(newFont);

        runButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                runTuring();
            }
        });
    }

    private void initLayout(){
        setLayout(new BorderLayout());
        JPanel topJPanel = new JPanel();
        
        topJPanel.add(runButton);
        add(topJPanel, BorderLayout.NORTH);
        add(new JScrollPane(outArea), BorderLayout.CENTER);
    }

    private void runTuring(){
        processFile();
    }

    private File selectFile(){
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private void processFile(){
        File machineFile = selectFile();
        if(machineFile == null){
            JOptionPane.showMessageDialog(this, "No file selected or operation cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
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
                            finalStates.add(state);
                        }
                    }
                }

                //Read the set of input alphabet
                int numTapeInputs = fileReader.nextInt();
                ArrayList<String> inputAlphabet = new ArrayList<>();

                for(int i = 0; i < numTapeInputs; i++){
                    inputAlphabet.add(fileReader.next()); //add object to array
                }
                
                //Read the set of output alphabet
                int numTapeOutputs = fileReader.nextInt();
                ArrayList<String> outputAlphabet = new ArrayList<>();

                for (int i = 0; i < numTapeOutputs; i++) {
                    outputAlphabet.add(fileReader.next());
                }

                //Read the number of tapes to be used in the Multi Tape Turing Machine 
                int numTapes = fileReader.nextInt();
                fileReader.nextLine();

                //Read the set of transition functions
                int numTransitions = fileReader.nextInt();
                fileReader.nextLine();
                System.out.println(numTransitions);
                Transition[] transitionArray = new Transition[numTransitions];

                for (int i = 0; i < numTransitions; i++) {
                    //read transition line
                    System.out.println("-----------------------");
                    String line = fileReader.nextLine();
                    //tokenize the input using space as a delimiter
                    String[] tokens = line.split(" ");
                    
                    int currentStateIndex = findIndex(tokens[0], stateArray);
                    State currentState = stateArray.get(currentStateIndex);
    
                    //compute for end of readsymbols;
                    int endRead = 1 + numTapes;

                    String[] readSymbols = new String[numTapes];
                    for (int j = 1; j < numTapes+1; j++) {
                        readSymbols[j-1] = tokens[j];
                        System.out.println("j = " + j + readSymbols[j-1] + "i = " + i);
                    }


                    String[] outSymbols = new String[numTapes];
                    int nextStateIndex = findIndex(tokens[endRead], stateArray);
                    State nextState = stateArray.get(nextStateIndex);
                    System.out.println(nextState.getName());
                    int startOut = endRead + 1; 
                    int index = 0;
                    for (int numOutSymbols = startOut; numOutSymbols < numTapes + startOut; numOutSymbols++){
                        outSymbols[index] = tokens[numOutSymbols];
                        System.out.println("outsymbol = " + outSymbols[index] + " loop=" + numOutSymbols);
                        index++;
                    }

                    int startDirection = startOut + numTapes;
                    index = 0;
                    System.out.println("Haruot");
                    String[] direction = new String[numTapes];
                    for (int j = startDirection; j < tokens.length; j++) {
                        direction[index] = tokens[j];
                        System.out.println("Direction = " + direction[index]);
                        index++;
                    }
                    transitionArray[i] = new Transition(currentState, readSymbols, nextState, outSymbols, direction);
                }

                for (Transition object : transitionArray) {
                    for (State state : stateArray) {
                        if(object.getCurrentState().equals(state)){
                            state.addTransition(object);
                            break;
                        }
                    }
                }


                //DONE: set is initial state
                /*
                * get the initial state of the machine and set it to the state
                */
                int iniitialStateIndex = findIndex(initialState, stateArray); 
                stateArray.get(iniitialStateIndex).setInitial(true);
                //DONE: set is final state
                /*
                * For each final state in the set of final states find the index within the array list
                * Uppon finding the index, get the associated state object and set it as a final state.
                */
                for (State finalState : finalStates) {
                    int finalStateIndex = findIndex(finalState.getName(), finalStates);
                    finalStates.get(finalStateIndex).setFinal(true);
                }

                outArea.append("Set of States = [ ");
                for (State state : stateArray) {
                    outArea.append(state.getName() + ", ");
                }
                outArea.append("]\n");
                outArea.append("Set of Input Symbols = [ ");
                for (String symbol : inputAlphabet) {
                    outArea.append(symbol + ", ");
                }
                outArea.append("]\n");

                outArea.append("Set of output Symbols = [ ");
                for (String symbol : outputAlphabet) {
                    outArea.append(symbol + ", ");
                }
                outArea.append("]\n");
                outArea.append("initial state = " + initialState+"\n");
                outArea.append("Final state/s = [ ");
                for (State state : finalStates) {
                    outArea.append(state.getName() + " ");
                }
                outArea.append("]\n\n");
                
                Tape[] tapeArray = new Tape[numTapes];
                for (int i = 0; i < numTapes; i++) {
                    tapeArray[i] = new Tape(stateArray, i);
                    if(fileReader.hasNextLine()){
                        String tapeInput = fileReader.nextLine();
                        
                        tapeArray[i].initializeTape(tapeInput);
                    }else{
                        tapeArray[i].initializeTape();
                    }
                }

                fileReader.close();
                //tape simulator


                int stepCounter = 0;
                for (State state : stateArray) {
                    for (int i = 0; i < state.getTransitionList().size(); i++) {
                        System.out.println("current state= " + state.getName() + " next =" + state.getTransitionList().get(i).getNextState().getName());
                    }
                }

                outArea.append("Simulating run...\n\n");
                while(tapesNotFinished(tapeArray)){
                    outArea.append("===============Step#" + stepCounter +"============\n\n");
                    for (int i = 0; i < tapeArray.length; i++) {
                        outArea.append("Tape number = " + i + " Current State = " + tapeArray[i].getCurrentState().getName() + " Reading =" + tapeArray[i].readSymbol()+"\n");
                        outArea.append("Before transition = " + tapeArray[i].getTapeContent()+"\n\n");
                        tapeArray[i].runTape();
                        outArea.append("After = " + tapeArray[i].getTapeContent()+"\n\n");
                    }
                    stepCounter++;
                }
                int allAccepted = 0;
                for (Tape tape : tapeArray) {
                    if (tape.isAccepted() == true){
                        allAccepted++;
                    } 
                }
                outArea.append("~~~~~~~~~~!V E R D I C T!~~~~~~~~~~~~~~\n\n");
                if(allAccepted == numTapes){
                    outArea.append("Output is: ");
                    //Append all the outputs of the tapes here
                    for (Tape tape : tapeArray) {
                        outArea.append(tape.getTapeContent()+ " ");
                    }
                    outArea.append("\n");
                }else{
                    outArea.append("! R E J E C T E D !");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private int findIndex(String stateName, ArrayList<State> list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(stateName)){
                return i;
            }
        }
        return -1;
    }
    private boolean tapesNotFinished(Tape[] tapes){
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



    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
                    TuringGui gui = new TuringGui();
                    gui.setVisible(true);
        });
    }
}
