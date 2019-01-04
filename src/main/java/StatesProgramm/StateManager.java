package StatesProgramm;

public class StateManager<K> {

    protected K lastState;
    protected K currentState;

    public final K getLastState() {
        return lastState;
    }

    public void setLastState(K lastState) {
        this.lastState = lastState;
    }

    public final K getCurrentState() {
        return currentState;
    }

    public void setCurrentState(K currentState) {
        this.lastState = this.currentState;
        this.currentState = currentState;
    }
    public void SwitchToLastState(){
        K tempState;

        tempState = this.currentState;
        this.currentState = this.lastState;
        this.lastState = tempState;
    }
}
