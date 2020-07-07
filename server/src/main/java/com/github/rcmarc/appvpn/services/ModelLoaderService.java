package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.impl.AttributeSelection;
import lombok.extern.slf4j.Slf4j;
import moa.classifiers.meta.AdaptiveRandomForest;
import moa.core.SerializeUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ModelLoaderService {

    private final String MODELS_DIR = "./src/main/resources/models/";
    private final String NORMALIZED_MODELS_DIR = MODELS_DIR + "normalized/";
    private final String NORMAL_MODELS_DIR = MODELS_DIR + "normal/";

    public AdaptiveRandomForest adaptiveRandomForest(AttributeSelection attributeSelection, boolean normalized) {
        try {
            final String path = normalized ? NORMALIZED_MODELS_DIR + attributeSelection : NORMAL_MODELS_DIR + attributeSelection;
            return (AdaptiveRandomForest) SerializeUtils.readFromFile(new File(path + "/AdaptiveRandomForest.moa"));
        }catch (IOException | ClassNotFoundException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

}
