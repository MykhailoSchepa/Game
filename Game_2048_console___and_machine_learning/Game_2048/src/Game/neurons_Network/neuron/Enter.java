package Game.neurons_Network.neuron;

public class Enter {

    private int enter;
    private double outer;

    public Enter(){
        this(0, 2048);
    }

    public Enter(int enter){
        this(enter, 2048);
    }

    public Enter(int enter, double maxEnters){
        this.enter = enter;
        outer = enter / maxEnters;
    }

    public void calculateOutput(int maxEnters){
        outer = enter / maxEnters;
    }

    public int getEnter() {
        return enter;
    }

    public void setEnter(int enter) {
        this.enter = enter;
    }

    public double getOuter() {
        return outer;
    }

    public void setOuter(double outer) {
        this.outer = outer;
    }
}
