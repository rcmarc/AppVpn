package com.github.rcmarc.appvpn.interfaces;

import com.github.rcmarc.appvpn.flow.PacketInfo;

public interface Flow {

    double[] timeBasedFeatures();

    int packetCount();

    void addPacket(PacketInfo packetInfo);

    long getFlowStartTime();

}
