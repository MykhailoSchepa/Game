package Game.neurons_Network;

import Game.neurons_Network.neuron.Neuron;

public class NeuronsNetwork {

    private Neuron[] neurons;
    private double[] enters;
    private double[] outers;

    public NeuronsNetwork(int outerLenght, int enterLength) {
        neurons = new Neuron[outerLenght];
        outers = new double[outerLenght];

        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(enterLength);
            outers[i] = 0;
        }
    }

    public NeuronsNetwork(double[] enters) {
        this(enters, enters.length);
    }

    public NeuronsNetwork(double[] enters, int outerLenght) {
        neurons = new Neuron[outerLenght];
        outers = new double[outerLenght];

                // для найпростішого випадку розміру поля 4*4
                // j - вниз (стовпець)
                // i - вліво (рядок)
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(enters);
            outers[i] = 0;
        }

                // при зміні даних в вхідному масиві через ф-ю міняються дані і в оригіналі
                // випадок непарною довжиною матриці
                // поки опустимо
        if (neurons.length / 2 == 0) {

        }
    }

    public double[] learningAndBackExpectedHiddenOuters(
            double learningSpeed, double[] expectedOuters
    ) {
        double[] backDeltaEnters = new double[enters.length];
        learning(learningSpeed, expectedOuters);

        for (int i = 0; i < backDeltaEnters.length; i++) {
            double delta = 0;
            for (Neuron n : neurons) {
                delta += n.getDelta(i);
            }
            backDeltaEnters[i] = delta / 2;
            backDeltaEnters[i] = enters[i] -  delta;
        }
        return backDeltaEnters;
    }

    public void learning(double learningSpeed, double[] expectedOuters) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].learning(expectedOuters[i], learningSpeed);
        }
    }

    public void countOuter(double[] enters) {
        this.enters = new double[enters.length];

        for (int i = 0; i < enters.length; i++) {
            this.enters[i] = enters[i];
        }
        countOuter();
    }

    public void countOuter() {
        for (int i = 0; i < neurons.length; i++) {
            outers[i] = neurons[i].countOuter(enters);
        }
    }

    public double[] getOuters() {
        return outers;
    }

    public void setEnters(double[] enters) {

        this.enters = new double[enters.length];

        for (int i = 0; i < enters.length; i++) {
            this.enters = enters;
        }
    }
}
