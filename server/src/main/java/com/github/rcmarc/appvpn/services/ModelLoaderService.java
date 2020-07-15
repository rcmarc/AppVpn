package com.github.rcmarc.appvpn.services;

import lombok.extern.java.Log;
import moa.classifiers.meta.AdaptiveRandomForest;
import moa.core.SerializeUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Log
public class ModelLoaderService {
    public AdaptiveRandomForest adaptiveRandomForest() {
        try {
            log.info("model loaded successfully");
            return (AdaptiveRandomForest) SerializeUtils
                    .readFromFile(new File(ModelLoaderService.class.getResource("AdaptiveRandomForest.moa").getPath()));
        } catch (ClassNotFoundException | IOException ex) {
            log.severe("Error loading model, cause: " + ex.getMessage());
            return null;
        }
    }

}
