package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.FlowGenerator;
import com.github.rcmarc.appvpn.interfaces.PacketFilter;
import com.github.rcmarc.appvpn.interfaces.PacketHandler;
import com.github.rcmarc.appvpn.interfaces.PacketReader;
import lombok.AllArgsConstructor;
import org.jnetpcap.packet.PcapPacket;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PacketHandlerImpl implements PacketHandler {

    private final PacketFilter packetFilter;
    private final FlowGenerator flowGenerator;
    private final PacketReader packetReader;

    @Override
    public void accept(PcapPacket packet) {
        if (packetFilter.passFilter(packet)) {
            flowGenerator.addPacket(packetReader.getPacketInfo(packet));
        }
    }
}
