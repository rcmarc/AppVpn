package com.github.rcmarc.appvpn.services;

import lombok.extern.java.Log;
import moa.classifiers.meta.AdaptiveRandomForest;
import moa.core.SerializeUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;

@Service
@Log
public class ModelLoaderService {

    public AdaptiveRandomForest adaptiveRandomForest() {
        try {
            ClassPathResource cpr = new ClassPathResource("AdaptiveRandomForest.moa", this.getClass());
            File tmp = File.createTempFile("tmp", "moa");
            byte[] buffer = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            FileCopyUtils.copy(buffer, tmp);
            return (AdaptiveRandomForest) SerializeUtils.readFromFile(tmp);
        } catch (Exception ex) {
            log.severe(ex.getMessage());
            return null;
        }
    }

}
