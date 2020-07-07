package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.AIModel;
import com.github.rcmarc.appvpn.interfaces.Flow;
import com.github.rcmarc.appvpn.interfaces.FlowListener;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlowListenerLogger implements FlowListener {
    private final AIModel classifier;

    @Override
    public void accept(Flow flow, String srcIp, String dstIp) {
        try {
            double[] features = flow.timeBasedFeatures();
            int n = classifier.classify(features);
            System.out.println((n == 0));
        } catch (IllegalArgumentException ignored) {

        }

    }
}
