package Game.neurons_Network;

public class Network {
    private int lengthEnters;
    private int lengthOuters;
    private int[] lengthOfHiddenLayers;

    private EnterLayer enterLayer;      //    можливо вхідного шару насправді не потрібно
    private NeuronsNetwork[] hiddenLayers;
    private NeuronsNetwork outerLayer;

    private double[] outers;
    private double[] enters;


    public Network(int lengthEnters, int lengthOuters, int... lengthOfHiddenLayers) {
        this.lengthEnters = lengthEnters;
        this.lengthOuters = lengthOuters;
        this.lengthOfHiddenLayers = lengthOfHiddenLayers;

        enterLayer = new EnterLayer(lengthEnters);

        int len = lengthEnters;

        if (lengthOfHiddenLayers.length > 0) {
            hiddenLayers = new NeuronsNetwork[lengthOfHiddenLayers.length];

            for (int i = lengthOfHiddenLayers.length - 1; i >= 0; i--) {
                hiddenLayers[i] = new NeuronsNetwork(lengthOfHiddenLayers[i], len);

                len = lengthOfHiddenLayers[i];
            }
        }

        outerLayer = new NeuronsNetwork(lengthOuters, len);
        outers = new double[lengthOuters];
    }

    private void countOuters() {

        double[] outerOfLayer = enters;

        if (lengthOfHiddenLayers.length > 0) {
            for (int i = 0; i < hiddenLayers.length; i++) {

                hiddenLayers[i].setEnters(outerOfLayer);

                hiddenLayers[i].countOuter();

                outerOfLayer = hiddenLayers[i].getOuters();
            }
        }
        if (lengthOfHiddenLayers.length > 0)
            outerLayer.setEnters(outerOfLayer);

        outerLayer.countOuter();
        outers = outerLayer.getOuters();
    }

    public void learn(double learningSpeed, double[] expectedOuters) {
        double[] expectedOutersHiddenLayer;

        expectedOutersHiddenLayer =
                outerLayer.learningAndBackExpectedHiddenOuters(
                        learningSpeed, expectedOuters);


        if (lengthOfHiddenLayers.length > 0){

            for (int i = 0; i < lengthOfHiddenLayers.length-1; i++) {
//                System.out.println("навчання шару 16 == " + expectedOutersHiddenLayer.length);
                expectedOutersHiddenLayer =
                        hiddenLayers[i].learningAndBackExpectedHiddenOuters(
                                learningSpeed, expectedOutersHiddenLayer);
            }
            hiddenLayers[hiddenLayers.length-1].
                    learning(learningSpeed, expectedOutersHiddenLayer);
        }
    }


    public double[] setOuter() {
        countOuters();
        return outers;
    }

    public void setEnters(double[] enters) {

        this.enters = new double[enters.length];

        for (int i = 0; i < enters.length; i++) {
            this.enters[i] = enters[i];
        }


        if (lengthOfHiddenLayers.length > 0)
            hiddenLayers[0].setEnters(enters);
        else outerLayer.setEnters(enters);
    }
}
