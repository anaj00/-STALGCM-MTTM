import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class State {
    private String name;
    private ArrayList<Transition> transitionList = new ArrayList<>();


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

    public void addTransition(Transition object){
        transitionList.add(object);
    }

    public ArrayList<Transition> getTransitionList() {
        return transitionList;
    }

    


    


    
}
