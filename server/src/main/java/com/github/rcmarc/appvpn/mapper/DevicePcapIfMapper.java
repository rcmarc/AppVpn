package com.github.rcmarc.appvpn.mapper;

import com.github.rcmarc.appvpn.data.Device;
import org.jnetpcap.PcapIf;

public interface DevicePcapIfMapper {

    Device map(PcapIf pcap, Long id);

}
