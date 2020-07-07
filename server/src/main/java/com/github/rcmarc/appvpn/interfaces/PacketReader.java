package com.github.rcmarc.appvpn.interfaces;

import com.github.rcmarc.appvpn.flow.PacketInfo;
import com.github.rcmarc.appvpn.flow.Protocol;
import org.jnetpcap.packet.PcapPacket;

public interface PacketReader {

    PacketInfo getPacketInfo(PcapPacket packet);

    PacketInfo getVPNInfo(PcapPacket packet, Protocol protocol);

    PacketInfo getIPv4Info(PcapPacket packet, Protocol protocol);

    PacketInfo getIPv6Info(PcapPacket packet, Protocol protocol);
}
