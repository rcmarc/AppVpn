package com.github.rcmarc.appvpn.services;

import lombok.extern.slf4j.Slf4j;
import moa.classifiers.meta.AdaptiveRandomForest;
import moa.core.SerializeUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ModelLoaderService {
    public AdaptiveRandomForest adaptiveRandomForest() {
        try {
            log.info("model loaded successfully");
            return (AdaptiveRandomForest) SerializeUtils
                    .readFromFile(new File(ModelLoaderService.class.getResource("AdaptiveRandomForest.moa").getPath()));
        } catch (ClassNotFoundException | IOException ex) {
            log.error("Error loading model, cause: " + ex.getMessage());
            return null;
        }
    }

}
