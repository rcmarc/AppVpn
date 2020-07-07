package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.flow.PacketInfo;
import com.github.rcmarc.appvpn.flow.Protocol;
import com.github.rcmarc.appvpn.interfaces.IdGenerator;
import com.github.rcmarc.appvpn.interfaces.PacketReader;
import com.github.rcmarc.appvpn.services.PropertiesService;
import lombok.AllArgsConstructor;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.vpn.L2TP;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PacketReaderImpl implements PacketReader {

    private final PropertiesService propertiesService;
    private final IdGenerator idGenerator;

    @Override
    public PacketInfo getPacketInfo(PcapPacket packet) {
        PacketInfo packetInfo = null;
        Boolean readIP4 = propertiesService.getIPv4();
        Boolean readIP6 = propertiesService.getIPv6();

        Protocol protocol = new Protocol();

        if (readIP4) {
            packetInfo = getIPv4Info(packet, protocol);
            if (packetInfo == null && readIP6) {
                packetInfo = getIPv6Info(packet, protocol);
            }
        }
        if (readIP6 && packetInfo == null) {
            packetInfo = getIPv6Info(packet, protocol);
        }

        if (packetInfo == null) {
            packetInfo = getVPNInfo(packet, protocol);
        }

        return packetInfo;
    }

    @Override
    public PacketInfo getVPNInfo(PcapPacket packet, Protocol protocol) {
        try {
            final Boolean readIPv4 = propertiesService.getIPv4();
            final Boolean readIPv6 = propertiesService.getIPv6();
            PacketInfo packetInfo = null;
            packet.scan(L2TP.ID);
            if (packet.hasHeader(protocol.getL2tp())) {
                if (readIPv4) {
                    packet.scan(protocol.getIpv4().getId());
                    packetInfo = getIPv4Info(packet, protocol);
                    if (packetInfo == null && readIPv6) {
                        packet.scan(protocol.getIpv6().getId());
                        packetInfo = getIPv6Info(packet, protocol);
                    }
                } else if (readIPv6) {
                    packet.scan(protocol.getIpv6().getId());
                    packetInfo = getIPv6Info(packet, protocol);
                    if (packetInfo == null && readIPv4) {
                        packet.scan(protocol.getIpv4().getId());
                        packetInfo = getIPv4Info(packet, protocol);
                    }
                }

            }
            return packetInfo;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public PacketInfo getIPv4Info(PcapPacket packet, Protocol protocol) {
        return getPacketInfo(packet, protocol, true);
    }

    @Override
    public PacketInfo getIPv6Info(PcapPacket packet, Protocol protocol) {
        return getPacketInfo(packet, protocol, false);
    }

    private PacketInfo getPacketInfo(PcapPacket packet, Protocol protocol, boolean isIPv4) {
        try {
            final PacketInfo packetInfo = isIPv4 ? getIPv4(packet,protocol) : getIPv6(packet,protocol);
            packetInfo.setTimeStamp(packet.getCaptureHeader().timestampInMillis());
            if (packet.hasHeader(protocol.getTcp())) {
                packetInfo.setSrcPort(protocol.getTcp().source());
                packetInfo.setDstPort(protocol.getTcp().destination());
                packetInfo.setPayloadBytes(protocol.getTcp().getPayloadLength());
                packetInfo.setProtocol(6);
            } else if (packet.hasHeader(protocol.getUdp())) {
                packetInfo.setSrcPort(protocol.getUdp().source());
                packetInfo.setDstPort(protocol.getUdp().destination());
                packetInfo.setPayloadBytes(protocol.getUdp().getPayloadLength());
                packetInfo.setProtocol(17);
            }
            return packetInfo;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


    private PacketInfo getIPv4(PcapPacket packet, Protocol protocol) {
        if (packet.hasHeader(protocol.getIpv4())) {
            PacketInfo packetInfo = new PacketInfo(idGenerator);
            packetInfo.setSrc(protocol.getIpv4().source());
            packetInfo.setDst(protocol.getIpv4().destination());
            return packetInfo;
        }
        return null;
    }

    private PacketInfo getIPv6(PcapPacket packet,Protocol protocol) {
        if (packet.hasHeader(protocol.getIpv4())) {
            PacketInfo packetInfo = new PacketInfo(idGenerator);
            packetInfo.setSrc(protocol.getIpv6().source());
            packetInfo.setDst(protocol.getIpv6().destination());
            return packetInfo;
        }
        return null;
    }
}
