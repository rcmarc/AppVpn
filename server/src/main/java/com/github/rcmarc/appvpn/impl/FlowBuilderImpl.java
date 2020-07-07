package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.flow.PacketInfo;
import com.github.rcmarc.appvpn.interfaces.Flow;
import com.github.rcmarc.appvpn.interfaces.FlowBuilder;
import com.github.rcmarc.appvpn.services.PropertiesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlowBuilderImpl implements FlowBuilder {

    private final PropertiesService propertiesService;

    @Override
    public Flow create(PacketInfo packetInfo) {
        return new FlowImpl(propertiesService, packetInfo);
    }
}
