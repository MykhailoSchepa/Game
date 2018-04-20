package Game.neurons_Network;

import Game.neurons_Network.neuron.Enter;

public class EnterLayer {

    private Enter[] enters;
    private int maxEnter;

    public EnterLayer(int lengthEnterLayer) {
        enters = new Enter[lengthEnterLayer];

        for (Enter e: enters) {
            e = new Enter();
        }
    }

    public void setEnterByI(int enter, int i){
        enters[i].setEnter(enter);
    }

    public int getEnterBuI(int i){
        return enters[i].getEnter();
    }

    public void findMaxEnter(){
        int max = Integer.MIN_VALUE;
        for (Enter enter: enters) {
            if (enter.getEnter() > max)
                max = enter.getEnter();
        }
        maxEnter = max;

        calculateOutputs();
    }

    private void calculateOutputs(){
        for (Enter e :
                enters) {
            e.calculateOutput(maxEnter);
        }
    }

    public double getOuterByI(int i){
        return enters[i].getOuter();
    }
}
