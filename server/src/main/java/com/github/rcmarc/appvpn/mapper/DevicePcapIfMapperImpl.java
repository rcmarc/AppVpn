package com.github.rcmarc.appvpn.mapper;

import com.github.rcmarc.appvpn.data.Device;
import org.jnetpcap.PcapIf;
import org.springframework.stereotype.Component;

@Component
public class DevicePcapIfMapperImpl implements DevicePcapIfMapper {
    @Override
    public Device map(PcapIf pcap, Long id) {
        return Device.builder()
                .id(id)
                .name(pcap.getName())
                .description(pcap.getDescription())
                .build();
    }
}
