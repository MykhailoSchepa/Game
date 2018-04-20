package Game.neurons_Network.neuron;

import java.util.Arrays;

public class Neuron {

    private double[] enters;
    private double[] weight;
    private double outer;

    private double expendedOuter;

    // крутизна функції
    private double t;


    private double b;

    public Neuron(int lengthWeight){
        weight = new double[lengthWeight];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = Math.random() * 0.6 + 0.2;      // присвоєння початкових ваг [0.2; 0.8)
        }

        t = 1;
    }

    public Neuron(double[] enters) {
        this.enters = Arrays.copyOf(enters, enters.length);

        weight = new double[enters.length];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = Math.random() * 0.6 + 0.2;      // присвоєння початкових ваг [0.2; 0.8)
        }

        t = 1;
    }

    public Neuron(double[] enters,
                  double[] weight,
                  double t,
                  double b) {
        for (int i = 0; i < enters.length; i++) {
            this.enters[i] = enters[i];
            this.weight[i] = weight[i];
        }

        this.t = t;
        this.b = b;
    }


    public double countOuter(double[] enters) {
//        System.out.println("sssss");
        outer = 0;

        this.enters = new double[enters.length];

        for (int i = 0; i < weight.length; i++) {      // при створенні шару нейронів змінив порядок створення
            this.enters[i] = enters[i];
            outer += enters[i] * weight[i];
        }

        outer += b;

        outer = activationFunction(outer);

        return outer;
    }

    private double activationFunction(double S) {
        double out = 1 / (1 + Math.exp((-1) * t * S));

        /// або лише при t=1 бо інакше похідна неправильна
//        double a = Math.exp(t*S);
//        double b = Math.exp(-t*S);
//        double out = (a-b)/(a+b);

        return out;
    }


    // функція виправляє помилку в всіх вагах свого шару
    public void learning(double expendedOuter, double learningSpeed) {
        // learningSpeed - швидкість навчання (зазвичай learningSpeed < 1)

        this.expendedOuter = expendedOuter;

        for (int i = 0; i < weight.length; i++) {

            double dE_dw = 2 * (outer - expendedOuter) *
                    enters[i] *
                    activationFunctionDerivative(outer);

            weight[i] = weight[i] - learningSpeed * dE_dw;
        }

        b = b - learningSpeed *
                2 *
                (outer - expendedOuter) *
                1 *
                activationFunctionDerivative(outer);
    }

    private double activationFunctionDerivative(double S) {
        double out;
        if (t != 1)
            out = t * Math.exp(-t * S) / Math.pow(1 + Math.exp(-t * S), 2);
        else
            out = S * (1 - S);

        /// або лише при t=1 бо інакше похідна неправильна
//        double a = Math.exp(t * S);
//        double b = Math.exp(-t * S);
//        out = 1 - Math.pow((b - a) / (b + a), 2);

        return out;
    }

    public double getDelta(int i){
        return (outer - expendedOuter)*
                weight[i]*
                activationFunctionDerivative(outer);
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getOuter() {
        return outer;
    }

    public double[] getEnters() {
        return enters;
    }

    public void setEnters(double[] enters) {
        for (int i = 0; i < enters.length; i++) {
            this.enters[i] = enters[i];
        }

    }

    public double getB() {
        return b;
    }

    public double getWeightByI(int i) {
        return weight[i];
    }

    public double getExpendedOuter() {
        return expendedOuter;
    }
}
