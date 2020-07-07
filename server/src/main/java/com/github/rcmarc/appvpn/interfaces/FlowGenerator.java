package com.github.rcmarc.appvpn.interfaces;

import com.github.rcmarc.appvpn.flow.PacketInfo;
public interface FlowGenerator {
    /**
     * This method will generate a {@link Flow} and it will call the {@code onFlowGenerated(Flow)} method
     * @param packet the {@link PacketInfo}
     */
     void addPacket(PacketInfo packet);

     FlowListener getFlowListener();

     default void onFlowGenerated(Flow flow, String srcIp, String dstIp){
         getFlowListener().accept(flow, srcIp, dstIp);
     }

}
