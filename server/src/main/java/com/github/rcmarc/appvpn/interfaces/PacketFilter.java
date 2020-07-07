package com.github.rcmarc.appvpn.interfaces;

import org.jnetpcap.packet.PcapPacket;

@FunctionalInterface
public interface PacketFilter {
    boolean passFilter(PcapPacket pcapPacket);
}
