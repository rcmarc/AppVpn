package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.interfaces.PacketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacketHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VpnService {

    private final PacketHandler packetHandler;
    private final PropertiesService propertiesService;
    private Pcap pcap;
    private Thread thread;

    public void loop(PcapIf pcapIf) {
        StringBuilder buffer = new StringBuilder();
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        thread = new Thread(() -> openLive(pcapIf.getName(), buffer));
        thread.start();
    }

    private void openLive(String name, StringBuilder buffer) {
        if (pcap != null) {
            pcap.close();
        }
        pcap = getPcap(name, buffer);
        if (pcap == null) {
            log.error(buffer.toString());
            return;
        }
        if (buffer.length() > 0) {
            log.warn(buffer.toString());
        }
        log.info("Listening on interface: " + name);
        PcapPacketHandler<Void> handler = (packet, uVoid) -> packetHandler.accept(packet);
        pcap.loop(-1, handler, null);
    }


    private Pcap getPcap(String name, StringBuilder buffer) {
        return Pcap.openLive(
                name,
                propertiesService.getSnapLength(),
                propertiesService.getPromiscuous(),
                propertiesService.getPcapTimeout(),
                buffer
        );
    }

}
