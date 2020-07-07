package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.PacketFilter;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.springframework.stereotype.Component;

@Component
public class PacketFilterImpl implements PacketFilter {
    @Override
    public boolean passFilter(PcapPacket pcapPacket) {
        return isTls(pcapPacket);
    }

    private boolean isTls(PcapPacket pcapPacket) {
        if (pcapPacket.hasHeader(new Payload())) {
            if (pcapPacket.getByte(0) == 0x45) {
                pcapPacket.scan(Ip4.ID);
                return tls(pcapPacket);
            }
        }
        return tls(pcapPacket);
    }

    private boolean tls(PcapPacket packet) {
        if (packet.hasHeader(new Http())) {
            return false;
        }
        Tcp tcp = new Tcp();
        if (packet.hasHeader(tcp)) {
            tcp = packet.getHeader(tcp);
            byte[] payload = tcp.getPayload();
            return payload.length > 1 && payload[1] == 0x03;
        }
        return false;
    }
}
