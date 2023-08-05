import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class State {
    private String name;
    private Map<Integer, ArrayList<Transition>> transitionMap = new HashMap<>();


    private boolean isInitial = false, isFinal = false;


    public State (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean isInitial() {
        return isInitial;
    }


    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }


    public boolean isFinal() {
        return isFinal;
    }


    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void addTransition(int tape, Transition object){
        //check if tape already exists 
        if(transitionMap.containsKey(tape)){
            transitionMap.get(tape).add(object); // add transition object into the tape mapping 
        }
        //add the tape number into the map and add the object
        else{
            transitionMap.put(tape, new ArrayList<>()); 
            transitionMap.get(tape).add(object);
        }
    }

    public Map<Integer, ArrayList<Transition>> getTransitionMap() {
        return transitionMap;
    }

    


    


    
}
