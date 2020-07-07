package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.flow.PacketInfo;
import com.github.rcmarc.appvpn.interfaces.Flow;
import com.github.rcmarc.appvpn.services.PropertiesService;
import lombok.Getter;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FlowImpl implements Flow {
    private final List<PacketInfo> forward = new ArrayList<>();
    private final List<PacketInfo> backward = new ArrayList<>();
    private final SummaryStatistics flowIAT = new SummaryStatistics();
    private final PropertiesService propertiesService;
    byte[] src;
    @Getter
    long flowStartTime;
    long flowLastSeen;

    public FlowImpl(PropertiesService propertiesService, PacketInfo packet) {
        this.propertiesService = propertiesService;
        this.src = packet.getSrc();
        this.firstPacket(packet);
    }

    private void firstPacket(PacketInfo packet) {
        flowStartTime = packet.getTimeStamp();
        flowLastSeen = packet.getTimeStamp();
        if (src == null) {
            src = packet.getSrc();
        }
        if (packet.isForwardPacket(src)) {
            forward.add(packet);
        } else {
            backward.add(packet);
        }
    }

    @Override
    public void addPacket(PacketInfo packet) {
        if (propertiesService.getFlowBidirectional()) {
            if (packet.isForwardPacket(src)) {
                forward.add(packet);
            } else {
                backward.add(packet);
            }
        } else {
            forward.add(packet);
        }
        flowIAT.addValue(packet.getTimeStamp() - flowLastSeen);
        flowLastSeen = packet.getTimeStamp();

    }

    @Override
    public double[] timeBasedFeatures() throws IllegalArgumentException{
        final double[] data = new double[2];
        final double duration = flowLastSeen - flowStartTime;
        final double d = ((double) packetCount()) / (duration / 1000000.0);
        if (Double.isInfinite(d)) {
            throw new IllegalArgumentException();
        }
        data[0] = d;
        data[1] = flowIAT.getMin();

        return data;
    }

    @Override
    public int packetCount() {
        if (propertiesService.getFlowBidirectional()) {
            return (forward.size() + backward.size());
        } else {
            return forward.size();
        }
    }
}
