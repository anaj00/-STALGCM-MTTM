import java.util.Set;

public class State {
    private String name;
    private Set <Transition> transitions;


    public State (String name){
        this.name = name;
    }

    
    // Initializes the transitions of the state
    public void initializeTransitions(Set<Transition> transitions){
        this.transitions = transitions;
    }


    // FIXME: Me donno yet what this means - bub
    public boolean isAccepted(String input) {
        for (Transition transition : transitions) {
            if (transition.getReadSymbol().equals(input)) {
                return true;
            }
        }
        return false;
    }


    
    public Set<Transition> getTransitions(){
        return transitions;
    }

    public String getName(){
        return name;
    }
}
