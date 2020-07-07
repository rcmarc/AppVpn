package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.AIModel;
import com.github.rcmarc.appvpn.services.MOADatasetLoaderService;
import com.github.rcmarc.appvpn.services.ModelLoaderService;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceImpl;
import com.yahoo.labs.samoa.instances.Instances;
import lombok.RequiredArgsConstructor;
import moa.classifiers.meta.AdaptiveRandomForest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CFSAdaptiveRandomForest implements AIModel {

    private final ModelLoaderService modelLoader;
    private final MOADatasetLoaderService datasetLoader;
    private AdaptiveRandomForest classifier;
    private Instances dataset;

    @PostConstruct
    private void init() {
        classifier = modelLoader.adaptiveRandomForest();
        dataset = datasetLoader.getCFSDataset();
        classifier.prepareForUse();
    }

    @Override
    public int classify(double[] data) {
        Instance instance = getInstance(data);
        return maxIndex(classifier.getVotesForInstance(instance));
    }

    @Override
    public void train(double[] data) {
        Instance instance = getInstance(data);
        classifier.trainOnInstanceImpl(instance);
    }

    private Instance getInstance(double[] data) {
        InstanceImpl instance = new InstanceImpl(1.0, data);
        instance.setDataset(dataset);
        return instance;
    }

    private int maxIndex(double[] d) {
        int index = 0;
        double max = d[0];
        for (int i = 1; i < d.length; i++) {
            if (d[i] > max) {
                index = i;
                max = d[index];
            }
        }
        return index;
    }
}
