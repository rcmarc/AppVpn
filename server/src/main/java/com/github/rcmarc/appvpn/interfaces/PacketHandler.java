package com.github.rcmarc.appvpn.interfaces;

import org.jnetpcap.packet.PcapPacket;

@FunctionalInterface
public interface PacketHandler {
    void accept(PcapPacket packet);
}
