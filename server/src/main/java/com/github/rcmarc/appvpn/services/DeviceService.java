package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.data.Device;
import com.github.rcmarc.appvpn.error.VpnException;
import com.github.rcmarc.appvpn.mapper.DevicePcapIfMapper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Service
@AllArgsConstructor
@Log
public class DeviceService {

    private final DevicePcapIfMapper devicePcapIfMapper;
    private final PropertiesService propertiesService;

    public List<PcapIf> getPcapIfs(){
        final StringBuilder builder = new StringBuilder();
        final ArrayList<PcapIf> devices = new ArrayList<>();
        int status = Pcap.findAllDevs(devices, builder);
        if (status == 1) {
            throw new VpnException(builder.toString());
        }
        return devices;
    }

    public List<Device> getAll() {
        final List<PcapIf> devices = getPcapIfs();
        return LongStream.range(1, devices.size() + 1).mapToObj(i -> devicePcapIfMapper.map(devices.get((int) i - 1), i))
                .collect(Collectors.toList());
    }

    public PcapIf select(Long index) {       
        propertiesService.setValue("devices.selected",index.toString());
        PcapIf pcap = getPcapIfs().get((int) (index - 1));
        log.info("Listening network traffic on " + pcap.getName());
        return pcap;
    }
}
