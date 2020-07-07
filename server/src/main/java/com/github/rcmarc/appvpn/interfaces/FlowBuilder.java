package com.github.rcmarc.appvpn.interfaces;

import com.github.rcmarc.appvpn.flow.PacketInfo;

public interface FlowBuilder {

    Flow create(PacketInfo packetInfo);

}
