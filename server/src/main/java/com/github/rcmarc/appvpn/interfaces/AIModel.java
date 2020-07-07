package com.github.rcmarc.appvpn.interfaces;

public interface AIModel {
    /**
     *
     * @param data The data of the numerical instance
     * @return the predicted class index
     */
    int classify(double[] data);

    /**
     *
     * @param data the data to be trained on
     */
    void train(double[] data);
}
